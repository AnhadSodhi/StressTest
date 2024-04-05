package p1;

import java.util.List;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.components.Gpu;
import com.profesorfalken.jsensors.model.sensors.Fan;
import com.profesorfalken.jsensors.model.sensors.Temperature;

public class Temps {
	
	public static String getCurrentCPU() {
		if (JSensors.get.components().cpus.size() != 0)
			return JSensors.get.components().cpus.get(0).name;
		else 
			return null;
	}
	
	public static double getCPUTemp() {
		if (JSensors.get.components().cpus.size() != 0) {
			Cpu cpu = JSensors.get.components().cpus.get(0);
			
			int numSensors = 0;
			double avg = 0;
			
			for (Temperature temp : cpu.sensors.temperatures) {
				numSensors++;
				avg += temp.value;
			}
			avg /= numSensors;
			
			return avg;
		}
		return 0.0;
	}
	
	public static String getCurrentGPU() {
		if (JSensors.get.components().gpus.size() != 0)
			return JSensors.get.components().gpus.get(0).name;
		else 
			return null;
	}
	
	public static double getGPUTemp() {
		if (JSensors.get.components().gpus.size() != 0) {
			Gpu gpu = JSensors.get.components().gpus.get(0);
			
			int numSensors = 0;
			double avg = 0;
			
			for (Temperature temp : gpu.sensors.temperatures) {
				numSensors++;
				avg += temp.value;
			}
			avg /= numSensors;
			
			return avg;
		}
		return 0.0;
	}
}
