package mmseg;

import java.io.IOException;

import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.Seg;

public class MaxWord extends Complex {

    public MaxWord(String path) {
        super(path);
    }

    protected Seg getSeg() {

		return new MaxWordSeg(dic);
	}

	public static void main(String[] args) throws IOException {
		new MaxWord("E:/WorkSpace/JavaDemo/elasticsearch-analysis-mmseg-5.4.1/config/mmseg/").run(args);
	}
}
