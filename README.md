

Mmseg Analysis for Elasticsearch
==================================

The Mmseg Analysis plugin integrates Lucene mmseg4j-analyzer:http://code.google.com/p/mmseg4j/ into elasticsearch, support customized dictionary.

The plugin ships with analyzers: `mmseg_dicfirst`, `mmseg_maxword`  ,`mmseg_complex` ,`mmseg_simple` and tokenizers: `mmseg_maxword`  ,`mmseg_complex` ,`mmseg_simple`  and token_filter: `cut_letter_digit` .

Meron changelog
==================================
·增加分词器:mmseg_dicfirst，在mmseg_maxword的基础上根据words-first.dic词典进行分词，只要输入语句中存在words-first.dic中的词，则肯定会拆出该词

Doc
==================================
·同义词和停词请使用es子自带的功能
·词典都是要UTF-8编码，Unix文件格式
·自定义词典名称必须是words*.dic
·mmseg_dicfirst分词器需要words-first.dic词典，找不到该词典则等同于mmseg_maxword


Versions
--------

Mmseg ver  | ES version
-----------|-----------
master | 5.x -> master
5.4.1 | 5.4.1


Package
-------------

```
mvn package
```

Install
-------------

Unzip and place into elasticsearch's plugins folder,
you can checkout example from https://github.com/medcl/elasticsearch-rtf
Download plugin from here: https://github.com/medcl/elasticsearch-analysis-mmseg/releases

Mapping Configuration
-------------

Here is a quick example:

1.Create a index

```
curl -XPUT http://localhost:9200/index

```

2.Create a mapping

```
curl -XPOST http://localhost:9200/index/fulltext/_mapping -d'
{
    "fulltext": {
             "_all": {
            "analyzer": "mmseg_maxword",
            "search_analyzer": "mmseg_maxword",
            "term_vector": "no",
            "store": "false"
        },
        "properties": {
            "content": {
                "type": "text",
                "store": "no",
                "term_vector": "with_positions_offsets",
                "analyzer": "mmseg_maxword",
                "search_analyzer": "mmseg_maxword",
                "include_in_all": "true",
                "boost": 8
            }
        }
    }
}'
```

3.Indexing some docs

```
curl -XPOST http://localhost:9200/index/fulltext/1 -d'
{"content":"美国留给伊拉克的是个烂摊子吗"}
'

curl -XPOST http://localhost:9200/index/fulltext/2 -d'
{"content":"公安部：各地校车将享最高路权"}
'

curl -XPOST http://localhost:9200/index/fulltext/3 -d'
{"content":"中韩渔警冲突调查：韩警平均每天扣1艘中国渔船"}
'

curl -XPOST http://localhost:9200/index/fulltext/4 -d'
{"content":"中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首"}
'
```

4.Query with highlighting

```
curl -XPOST http://localhost:9200/index/fulltext/_search  -d'
{
    "query" : { "term" : { "content" : "中国" }},
    "highlight" : {
        "pre_tags" : ["<tag1>", "<tag2>"],
        "post_tags" : ["</tag1>", "</tag2>"],
        "fields" : {
            "content" : {}
        }
    }
}
'
```

Here is the query result

```

{
    "took": 14,
    "timed_out": false,
    "_shards": {
        "total": 5,
        "successful": 5,
        "failed": 0
    },
    "hits": {
        "total": 2,
        "max_score": 2,
        "hits": [
            {
                "_index": "index",
                "_type": "fulltext",
                "_id": "4",
                "_score": 2,
                "_source": {
                    "content": "中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首"
                },
                "highlight": {
                    "content": [
                        "<tag1>中国</tag1>驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首 "
                    ]
                }
            },
            {
                "_index": "index",
                "_type": "fulltext",
                "_id": "3",
                "_score": 2,
                "_source": {
                    "content": "中韩渔警冲突调查：韩警平均每天扣1艘中国渔船"
                },
                "highlight": {
                    "content": [
                        "均每天扣1艘<tag1>中国</tag1>渔船 "
                    ]
                }
            }
        ]
    }
}

```


Have fun.
