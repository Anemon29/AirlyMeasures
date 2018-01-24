# AirlyMeasures
Console application which shows info from a chosen Airly sensor

To show help, use program argument "-h", "--help"

To get an info from a chosen Airly sensor, you must provide adequate parameters: longitude and latitude OR sensor-id.

If chosen program arguments are longitude (-long, --longitude) and latitude(-lat, --latitude):
the nearest sensor in approx. 1000m radius will be searched for, and his measurements will be output.
  
If chosen program argument is sensor(-s, --sensor-id):
  data from this particular sensor will be output. Bear in mind, that there are a lot of invalid sensor IDs.
  In some cases, the table with measurements will display a lot of "No data" values.
  
Adding -hist program argument is optional and it changes the way data is displayed. Instead of current measurements, you will get a big table of measurements from the last 24 hours.

API-key, which is required for the program to work, is being read from the enviroment variables (under the key "API-KEY").
Another way to authorise yourself is to provide own airly API-key by putting -ak <Your API-KEY> program argument.
