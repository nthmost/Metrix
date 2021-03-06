// Illumina Metrix - A server / client interface for Illumina Sequencing Metrics.
// Copyright (C) 2013 Bernd van der Veen

// This program comes with ABSOLUTELY NO WARRANTY;
// This is free software, and you are welcome to redistribute it
// under certain conditions; for more information please see LICENSE.txt

package nki.parsers.illumina;

import nki.io.LittleEndianInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

public class ErrorMetrics {
	private String source = "";
	LittleEndianInputStream leis = null;

	private int version = 0;
	private int recordLength = 0;

	public ErrorMetrics(String source){
		try{
			setSource(source);
			leis = new LittleEndianInputStream(new FileInputStream(source));
		}catch(IOException IO){
			System.out.println("Parser Error - Error Metrics: " + IO.toString());
		}
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	private void setVersion(int version){
		this.version = version;
	}

	public int getVersion(){
		return version;
	}

	private void setRecordLength(int recordLength){
		this.recordLength = recordLength;
	}

	public int getRecordLength(){
		return recordLength;
	}

	public void outputData(){
		try{
			setVersion(leis.readByte());
			setRecordLength(leis.readByte());		
		}catch(IOException Ex){
			System.out.println("Error in parsing version number and recordLength: " + Ex.toString());
		}

		try{
			int record = 1;

			while(true){
				int laneNr = leis.readUnsignedShort();
				int tileNr = leis.readUnsignedShort();
				int cycleNr = leis.readUnsignedShort();
				float errorRate = leis.readFloat();
				int numPerfectReads = leis.readInt();
				int numReads1E = leis.readInt();
				int numReads2E = leis.readInt();
				int numReads3E = leis.readInt();
				int numReads4E = leis.readInt();
				
				System.out.println(laneNr + "\t" + cycleNr + "\t" + tileNr + "\t" + errorRate + "\t" + numPerfectReads + "\t" + numReads1E + "\t" + numReads2E + "\t" + numReads3E + "\t" + numReads4E);

			}
		}catch(EOFException EOFEx){
			System.out.println("Reached end of file");
		}catch(IOException Ex){
			System.out.println("IO Error");
		}

	}
}
