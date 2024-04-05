package p1;

import java.nio.FloatBuffer;

import org.jocl.*;
import com.jogamp.opencl.*;
import com.jogamp.opencl.CLMemory.Mem;

public class GPUTest {
	public static void main(String[] args) {
        // Initialize OpenCL
        CL.setExceptionsEnabled(true);
        CLPlatform platform = CLPlatform.getDefault();
        CLDevice device = platform.getMaxFlopsDevice(CLDevice.Type.GPU);
        CLContext context = CLContext.create(device);
        CLCommandQueue queue = device.createCommandQueue();

        // Set up kernel
        String kernelSource =
            "__kernel void stressTest(__global float* output, uint count) {" +
            "    int i = get_global_id(0);" +
            "    if (i < count) {" +
            "        float result = 0.0f;" +
            "        for (int j = 0; j < 1000000; j++) {" +
            "            result += sin((float)j) * cos((float)j);" +
            "        }" +
            "        output[i] = result;" +
            "    }" +
            "}";
        CLProgram program = context.createProgram(kernelSource);
        program.build();
        CLKernel kernel = program.createCLKernel("stressTest");

        // Set up data
        final int numElements = 1024;
        float[] output = new float[numElements];
        CLBuffer<FloatBuffer> outputBuffer = context.createFloatBuffer(numElements, Mem.READ_WRITE);
        outputBuffer.getBuffer().put(output).rewind();

        // Set kernel arguments
        kernel.setArg(0, outputBuffer);
        kernel.setArg(1, numElements);

        // Enqueue the kernel for execution
        long[] globalWorkSize = { numElements };
        while (true) { // Infinite loop
            queue.putWriteBuffer(outputBuffer, false);
            queue.put1DRangeKernel(kernel, 0, numElements, 0);
            queue.putReadBuffer(outputBuffer, true);

            // Read the results back from the device
            outputBuffer.getBuffer().rewind();
            outputBuffer.getBuffer().get(output).rewind();
        }
    }
}
