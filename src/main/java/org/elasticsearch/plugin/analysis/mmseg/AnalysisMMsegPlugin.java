/*
 * Licensed to Elastic Search and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Elastic Search licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.plugin.analysis.mmseg;

import com.chenlb.mmseg4j.Dictionary;
import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.HashMap;
import java.util.Map;


public class AnalysisMMsegPlugin extends Plugin implements AnalysisPlugin {

    final Dictionary dic = Dictionary.getInstance();

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
        Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> extra = new HashMap<>();

        extra.put("mmseg_dicfirst", MMsegTokenizerFactory::getDicFirst);
        extra.put("mmseg_maxword", MMsegTokenizerFactory::getMaxWord);
        extra.put("mmseg_complex", MMsegTokenizerFactory::getComplex);
        extra.put("mmseg_simple", MMsegTokenizerFactory::getSimple);

        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<org.elasticsearch.index.analysis.TokenFilterFactory>> getTokenFilters()  {
        Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> extra = new HashMap<>();
        extra.put("cut_letter_digit", CutLetterDigitTokenFilter::new);

        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new HashMap<>();

        extra.put("mmseg_dicfirst", MMsegAnalyzerProvider::getDicFirst);
        extra.put("mmseg_maxword", MMsegAnalyzerProvider::getMaxWord);
        extra.put("mmseg_complex", MMsegAnalyzerProvider::getComplex);
        extra.put("mmseg_simple", MMsegAnalyzerProvider::getSimple);

        return extra;
    }
}
