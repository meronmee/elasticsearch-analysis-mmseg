package mmseg;

import java.io.IOException;

import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.SimpleSeg;

/**
 * 
 * @author chenlb 2009-3-14 上午12:38:40
 */
public class Simple extends Complex {

    public Simple(String path) {
        super(path);
    }

    protected Seg getSeg() {
		return new SimpleSeg(dic);
	}

	public static void main(String[] args) throws IOException {
		new Simple("D:/Dev/elasticsearch-5.4.1/plugins/elasticsearch-analysis-mmseg-5.4.1/config/mmseg/").run(args);
	}

}