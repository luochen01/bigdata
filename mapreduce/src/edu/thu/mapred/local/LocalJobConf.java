package edu.thu.mapred.local;

import com.aliyun.odps.Column;
import com.aliyun.odps.conf.Configuration;
import com.aliyun.odps.mapred.conf.JobConf;
import com.aliyun.odps.mapred.utils.SchemaUtils;

import edu.thu.mapred.local.util.RecordComparator;

public class LocalJobConf extends JobConf {

	private static final String IO_MERGE_COMBINE = "io.merge.combine";

	private static final String IO_INDEX_PER = "io.sort.per";

	private static final String IO_SORT_MB = "io.sort.mb";

	private static final String IO_SPILLER = "io.spiller";

	private static final String IO_SORT_FACTOR = "io.sort.factor";

	private static final String MAP_THREADS = "map.threads";

	private static final String LOCAL_JOB_DIR = "local.job.dir";

	private static final String OUTPUT_SCHEMA = "output.schema";

	private static final String INPUT_PATH = "input.path";

	private static final String IO_READ_BUFFER_SIZE = "io.read.buffer.size";

	private static final String IO_WRITE_BUFFER_SIZE = "io.write.buffer.size";

	private static final String MAP_SIMPLE_COLLECTOR = "map.simple.collector";

	private static final String SPILL_BUFFER_MB = "spill.buffer.mb";

	public LocalJobConf(Configuration conf) {
		super(conf);
	}

	public String getJobDir() {
		return get(LOCAL_JOB_DIR);
	}

	public String getMapDir() {
		return get(LOCAL_JOB_DIR) + "map/";
	}

	public String getReduceDir() {
		return get(LOCAL_JOB_DIR) + "reduce/";
	}

	public void setJobDir(String dir) {
		set(LOCAL_JOB_DIR, dir);
	}

	public String getMapInputPath() {
		return get(INPUT_PATH);
	}

	public void setMapInputPath(String path) {
		set(INPUT_PATH, path);
	}

	public Column[] getOutputSchema() {
		String schema = get(OUTPUT_SCHEMA);
		if (schema == null) {
			schema = "word:string,count:bigint";
			set(OUTPUT_SCHEMA, schema);
		}
		return SchemaUtils.fromString(schema);
	}

	@Override
	public String get(String name) {
		String value = super.get(name);
		if (value == null) {
			value = System.getProperty(name);
		}
		return value;
	}

	/**
	 * Important
	 * @return
	 */
	public float getSpiller() {
		return getFloat(LocalJobConf.IO_SPILLER, 0.6f);
	}

	public int getSortMB() {
		return getInt(LocalJobConf.IO_SORT_MB, 100);
	}

	public float getIndexPer() {
		return getFloat(LocalJobConf.IO_INDEX_PER, 0.2f);
	}

	public int getMapThreads() {
		return getInt(LocalJobConf.MAP_THREADS, 1);
	}

	public int getSortFactor() {
		return getInt(LocalJobConf.IO_SORT_FACTOR, 10);
	}

	public boolean getSimpleCollector() {
		return getBoolean(LocalJobConf.MAP_SIMPLE_COLLECTOR, true);
	}

	public RecordComparator getMapOutputKeyComparator() {
		return new LocalRecord.DefaultRecordComparator(getMapOutputKeySchema());
	}

	public boolean getMergeCombine() {
		return getBoolean(LocalJobConf.IO_MERGE_COMBINE, true);
	}

	public int getReadBufferSize() {
		return getInt(LocalJobConf.IO_READ_BUFFER_SIZE, 512) * 1024;
	}

	public int getWriteBufferSize() {
		return getInt(LocalJobConf.IO_WRITE_BUFFER_SIZE, 512) * 1024;
	}

	public int getSpillBufferMB() {
		return getInt(LocalJobConf.SPILL_BUFFER_MB, 150);
	}
}
