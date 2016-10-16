# The Multicorder

The Multicorder (copyright pending) is a smartphone, phone case that will make your smartphone even smarter by adding widenned sensor capabilities to it.  With 10 sensors (interfaced to the phone by a Teensy-esque microcontroller) the Labcase will be able to not only measure all 7 SI base units, but also 15 additional measurements like potential difference, reistance, barometric pressure and more!

This repo has both the actual device coding as well as the DB coding and phone app codes.

Current list of sensors:
  - Barometric Pressure, Humidity, Temperature, and Light Sensor Breakout
  - Gas (CO, Ethanol, Hydrocarbons) Concentration Sensor
  - Force Sensor
  - Conductivity Sensor
  - Mutlimeter (Potential Difference, Current, Resistance)
  - Protractor / Linear distance sensor
  - UV Sensor
  
Current list of supported measurements and derived measurements: (**Bolded measurements are Base Units**)
  - **Linear distance (m)** 
    - Volume (m^3)
  - Force (N / lb)
    - **Mass (kg)**
    - Density (kg / m^3)
  - **Time (s)**
  - Angles (degrees / radians)
  - **Luminous intensity (candellas)**
    - Irradiance (W * m^2)
  - Potential difference (V)
    - Resistance (Ohms)
    - **Current (A)**
    - Conductivity (siemens per meter)
      - Salinity (ppm)
  - Atmospheric Pressure (atm)
  - **Ambient Temperature, liquid temperature (C, K, F)**
    - **moles of a gas (mol)**
  - Humidity (%)
  - UV Intensity (mW / cm^2)
  - Concentration of gases (ppm)
  - Color sensing (qualitative)
