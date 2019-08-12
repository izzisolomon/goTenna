# goTenna

The project took about 4 hours. another hour was spent on reading on on the MapBox SDK.

## Notable Dependencies

- Retrofit2 (for networking)
- Room (for sqlite processing)
- MapBox (Map SDK)

## Project Structure

```
|-- com.gotenna
  |-- controllers - view controllers
  |-- data - DB structure and Json data classes
  |-- models - Data and network handlers
  |-- views - view classes
```


## Language

All of the code was written in Kotlin, except for the DataManger class (located in com.gotenna/models). I figured one class is enough to demonstrate some Java knowledge.

## Workflow

#### Data retrieval and storage

Initially, the data is requested from the provided url, and is converted to Java Objects using the Moshi Factory.
The data is then stored in a local SQLite DB, using the Room framework. Room makes it easy to store and retrieve the data as java objects.

#### The Map

The map is using the "Street View" style by default, but it can be changed using the Floating Settings Button.
If the user allows location access, the map will display the current location. 
The map contains markers on all the locations in the data set. Clicking on a marker will scroll to that particular item in the List, and highlight it.

#### The List

The list (located below the map) contains the names and descriptions in the data set. Clicking on an item will zoom in on that particular marker on the map.





