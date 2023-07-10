package com.hientran.sohebox.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.util.FileCopyUtils;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

	private static final int BUFFER_SIZE = 1024;

	private static final String FILE_ENCODING = "UTF-8";

	/**
	 * FileUtils default constructor
	 */
	private FileUtils() {
	}

	/**
	 * Write file
	 *
	 * @param dataFile the data file
	 * @throws IOException the IOException
	 */
	public static void writeFile(DataFile dataFile) throws IOException {
		// Save image.
		InputStream inputStream = null;
		if (dataFile != null) {
			String ext = dataFile.getExtension();
			String fileName = StringUtils.trimToEmpty(dataFile.getFileName());
			if (StringUtils.isBlank(fileName)) {
				log.error("Can not write file because file name is blank");
				return;
			}
			DataHandler dataHandler = dataFile.getFile();
			if (dataHandler != null) {
				inputStream = dataHandler.getInputStream();
				File file = new File(fileName + ext);
				writeFile(inputStream, file);
			}
		}
	}

	/**
	 * Write Input Stream to File
	 *
	 * @param inputStream the InputStream
	 * @param file        the Output File
	 * @throws IOException the IOException
	 */
	public static void writeFile(InputStream inputStream, File file) throws IOException {
		OutputStream outputStream = null;
		try {
			outputStream = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buffer = new byte[BUFFER_SIZE];
			int len;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e1) {
					log.error(ExceptionUtils.getFullStackTrace(e1));

				}
			}
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e1) {
					log.error(ExceptionUtils.getFullStackTrace(e1));

				}
			}
		}
	}

	/**
	 * Get the number of bytes of an input stream
	 *
	 * @param inputStream InputStream
	 * @return the number of bytes
	 * @throws IOException the IOException
	 */
	public static int getByte(InputStream inputStream) throws IOException {
		int bytes = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		int len;
		while ((len = inputStream.read(buffer)) != -1) {
			bytes += len;
		}

		return bytes;
	}

	/**
	 * Convert the InputStream to String.
	 *
	 * @param is the InputStream
	 * @return string
	 * @throws IOException IOException
	 */
	public static String getString(InputStream is) throws IOException {
		String text = "";
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[BUFFER_SIZE];
			Reader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(is, FILE_ENCODING));
				int len;
				while ((len = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, len);
				}
				text = writer.toString();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						log.error(ExceptionUtils.getFullStackTrace(e));

					}
				}
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					log.error(ExceptionUtils.getFullStackTrace(e));

				}
			}

		}

		return text;
	}

	/**
	 * Create directory if it does not exist
	 *
	 * @param directory the directory to create
	 */
	public static void createDirIfNotExist(String directory) {
		Validate.isTrue(StringUtils.isNotBlank(directory), "The directory must be defined");
		File file = new File(directory);
		if (!file.exists()) { // directory does not exist
			boolean r = file.mkdir(); // create it
			if (!r) {
				log.debug("Can not create folder : " + directory);
			}
		}
	}

	/**
	 * Create directories if it does not exist
	 *
	 * @param directory the directory to create
	 */
	public static void createDirsIfNotExist(String directory) {
		Validate.isTrue(StringUtils.isNotBlank(directory), "The directory must be defined");
		try {
			Files.createDirectories(Paths.get(directory));
		} catch (IOException e) {
			log.error("Creation of folder + " + directory + " failed.", e);
		}

	}

	/**
	 * Close a stream.
	 *
	 * @param closeable
	 */
	public static void closeQuietly(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				// Do nothing
			}
		}
	}

	public static boolean isAccessable(File file) {
		boolean result = false;
		RandomAccessFile randomAccessFile = null;
		FileLock tryLock = null;
		String logMessage = "Could not access to ";
		if (file != null && file.exists()) {
			try {
				randomAccessFile = new RandomAccessFile(file, "rw");
				tryLock = randomAccessFile.getChannel().tryLock();
				if (tryLock != null) {
					tryLock.release();
					result = true;
				}
			} catch (FileNotFoundException e) {
				log.warn(logMessage + file);
			} catch (IOException e) {
				log.warn(logMessage + file);
			} finally {
				FileUtils.closeQuietly(randomAccessFile);
			}
		}
		return result;
	}

	/**
	 * Explanation of processing
	 *
	 * @param file
	 * @return
	 */
	public static String getCanonicalPath(File file) {
		try {
			return file.getCanonicalPath();
		} catch (Exception e) {
			return file.getAbsolutePath();
		}
	}

	/** This may fail for VERY large files. */
	public static void copyWithChannels(File aSourceFile, File aTargetFile, boolean aAppend) {
		log.info("Copying files with channels.");
		ensureTargetDirectoryExists(aTargetFile.getParentFile());
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		try {
			try {
				inStream = new FileInputStream(aSourceFile);
				inChannel = inStream.getChannel();
				outStream = new FileOutputStream(aTargetFile, aAppend);
				outChannel = outStream.getChannel();
				long bytesTransferred = 0;
				// defensive loop - there's usually only a single iteration :
				while (bytesTransferred < inChannel.size()) {
					bytesTransferred += inChannel.transferTo(0, inChannel.size(), outChannel);
				}
			} finally {
				// being defensive about closing all channels and streams
				if (inChannel != null) {
					inChannel.close();
				}
				if (outChannel != null) {
					outChannel.close();
				}
				if (inStream != null) {
					inStream.close();
				}
				if (outStream != null) {
					outStream.close();
				}
			}
		} catch (FileNotFoundException ex) {
			log.error("File not found: " + ex);
		} catch (IOException ex) {
			log.error(ExceptionUtils.getFullStackTrace(ex));

		}
	}

	private static void ensureTargetDirectoryExists(File aTargetDir) {
		if (!aTargetDir.exists()) {
			aTargetDir.mkdirs();
		}
	}

	/**
	 * Check for a path existed.
	 *
	 * @param monitorPath Watching path
	 * @return Path exists or not
	 */
	public static boolean exist(String monitorPath) {
		try {
			return new File(monitorPath).exists();
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Copy file using stream
	 *
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			os.flush();
		} finally {
			if (null != is) {
				is.close();
			}
			if (null != os) {
				os.close();
			}
		}
	}

	/**
	 * get File from pathFile
	 *
	 * @param filePath the path store file
	 * @param fileType type of file (jped/image, ...)
	 * @return DataFile
	 * @throws IOException exception when read file
	 */
	public static DataFile readFile(String filePath, String fileType) throws IOException {
		DataFile dataFile = null;
		if (StringUtils.isNotEmpty(filePath)) {
			File file = new File(filePath);
			if (file.exists()) {
				DataSource ds = new FileDataSource(file);
				DataHandler handler = new DataHandler(ds);
				dataFile = new DataFile(handler);
			}
		}

		return dataFile;
	}

	/**
	 * Get PhotoFile base on filePath
	 *
	 * @param filePath
	 * @return DataFile
	 * @throws IOException readFile exception
	 */
	public static DataFile getPhotoFile(String filePath) throws IOException {
		DataFile dataFile = null;
		if (StringUtils.isNotEmpty(filePath)) {
			String imageType = "image";
			// Read File
			dataFile = FileUtils.readFile(filePath, imageType);

			// Set info : fileName, extension File
			String[] imageInfoArr = filePath.split("\\/");
			if (imageInfoArr != null && imageInfoArr.length > 0) {
				// Set fileName
				String fileName = imageInfoArr[imageInfoArr.length - 1];
				dataFile.setFileName(fileName);

				// Set extension
				String[] fileInfoArr = fileName.split("\\.");
				if (fileInfoArr != null && fileInfoArr.length > 0) {
					dataFile.setExtension(fileInfoArr[fileInfoArr.length - 1]);
				}
			}
		}
		return dataFile;
	}

	/**
	 * Convert String to FileInputStream
	 *
	 * @param fileString data file
	 * @return InputStreamS
	 * @throws IOException
	 */
	public static InputStream getStreamFromString(String fileString) throws IOException {
		byte[] decodeBase64 = Base64.getDecoder().decode(fileString);
		InputStream is = new ByteArrayInputStream(decodeBase64);
		return is;
	}

	/**
	 *
	 * Convert file to string data.
	 *
	 * @param file File object
	 * @return String data
	 * @throws IOException
	 */
	public static String getStringFromStream(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		byte[] fileByte = new byte[(int) file.length()];
		fis.read(fileByte);
		fis.close();

		return Base64.getEncoder().encodeToString(fileByte);
	}

	/**
	 * Delete existing file.
	 *
	 * @param fileName File path
	 */
	public static void deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			file.deleteOnExit();
		}
	}

	/**
	 * Copy file
	 *
	 * @param srcFilePath
	 * @param desFilePath
	 * @throws IOException
	 */
	public static void copyFile(String srcFilePath, String desFilePath) throws IOException {
		File src = new File(srcFilePath);
		File des = new File(desFilePath);

		FileCopyUtils.copy(src, des);
	}

	/**
	 * Read local file
	 *
	 */
	public static String readLocalFile(String filePath) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		return new String(encoded, StandardCharsets.UTF_8);
	}

	/**
	 * Update JSON file
	 *
	 * @throws IOException
	 *
	 */
	public static void updateJsonDataRequest(String jsonData, String filePath) throws IOException {

		// Prepare destination file
		File desFile = new File(filePath);
		if (!FileUtils.exist(filePath)) {
			desFile.createNewFile();
		}

		// Prepare input string
		InputStream inputStream = new ByteArrayInputStream(jsonData.getBytes());

		// Write
		FileUtils.writeFile(inputStream, new File(filePath));
	}
}
