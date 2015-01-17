package edu.thu.mapred.local;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.odps.mapred.TaskId;

import edu.thu.mapred.local.io.TaskFileHelper;

public abstract class BaseDriver implements Runnable {

	protected LocalJobConf conf;
	protected TaskId id;
	protected List<File> mapFiles;
	protected TaskFileHelper fileHelper;

	protected static Logger logger = LoggerFactory.getLogger(BaseDriver.class);

	public BaseDriver(LocalJobConf conf) {
		this(conf, null);
	}

	public BaseDriver(LocalJobConf conf, List<File> mapFiles) {
		this.conf = conf;
		this.mapFiles = mapFiles;
		this.fileHelper = new TaskFileHelper(this);
	}

	/**
	 * Reuse
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void init(TaskId id) throws Exception {
		this.id = id;
		File dir = new File(getTaskDir());
		dir.mkdir();
	}

	public abstract String getTaskDir();

	public abstract void runInternal() throws Exception;

	@Override
	public final void run() {
		try {
			runInternal();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
