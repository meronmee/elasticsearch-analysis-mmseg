package mmseg;

import java.io.IOException;

import com.chenlb.mmseg4j.DicFirstSeg;
import com.chenlb.mmseg4j.Seg;

public class DicFirst extends Complex {

    public DicFirst(String path) {
        super(path);
    }

    protected Seg getSeg() {
		return new DicFirstSeg(dic);
	}

	public static void main(String[] args) throws IOException {
		new DicFirst("E:/WorkSpace/JavaDemo/elasticsearch-analysis-mmseg-5.4.1/config/mmseg/").run(args);
	}
}
