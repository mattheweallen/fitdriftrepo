package com.fitdrift.util.file.fit;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fitdrift.domain.activity.ActivityPoint;
import com.garmin.fit.Decode;
import com.garmin.fit.FitRuntimeException;
import com.garmin.fit.MesgBroadcaster;
import com.garmin.fit.RecordMesgListener;

/**
 * This class is creates a Fit Listener and returns the Activity points
 * found by the listener.
 * 
 * @author Matthew Allen
 * @version 20131208
 *
 */
public class FitDecoder {
	public static List<ActivityPoint> decode(InputStream in) throws IOException {
		Decode decode = new Decode();
		MesgBroadcaster mesgBroadcaster = new MesgBroadcaster(decode);
		FitListener listener = new FitListener();
		

//		try {
//			if (!Decode.checkIntegrity((InputStream) in))
//				throw new RuntimeException("FIT file integrity failed.");
//		} finally {
//			try {
//				in.close();
//			} catch (java.io.IOException e) {
//				throw new RuntimeException(e);
//			}
//		}
		mesgBroadcaster.addListener((RecordMesgListener) listener);

		//try {
		mesgBroadcaster.run(in);
		//} catch (FitRuntimeException e) {
		//	System.err.print("Exception decoding file: ");
		//	System.err.println(e.getMessage());

		//	try {
		in.close();
		//	} catch (java.io.IOException f) {
		//		throw new RuntimeException(f);
		//	}

		//	return;
		//}

	//	try {
		//	in.close();
	//	} catch (java.io.IOException e) {
		//	throw new RuntimeException(e);
	//	}

		//System.out.println("Decoded FIT file.");
		return listener.getActivityPoints();
	}
	
	
}
