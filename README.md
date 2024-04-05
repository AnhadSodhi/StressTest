# StressTest

### How it works:
* The CPU stress test generates random numbers (works on any CPU)
* The GPU stress test writes, reads and clears the buffer of all the cores using OpenCL (works on any GPU, including integrated graphics)
* The Temps processses the calculations for the current CPU and GPU temperatures, along with sending the names of the CPU and GPU to the GUI
* The GUI displays the names and current average core temperatures of the CPU and GPU being used, along with a start and stop button for the test

### How to run it:
* Must hava Java installed
* Clone this repository and open it as a project in your favourite IDE (Eclipse IDE was used for this project)
* Manually add all of the .jar files included to the build path
* Run Main.java

#### Thanks to JogAmp.org, JOCL.org, and profesorfalken (https://github.com/profesorfalken/jSensors) for the libraries used in this project
