TODO
====

add support for binary devices
------------------------------

- support for binary devices
    - decide at the time of connection the mode to use
        - text
        - binary, with 16 bytes buffer
            - can be configurable later if there is demand and justification
    - simplest possible UI to choose the mode (pop-up)
    - record binary data, but display in hexdump format
        - verify content of attachment
- lite version
    - always binary mode, not possible to select text
- release
    - release apk
    - update description
    - update screenshots
- increase price of full app to 3 EUR
- important improvements soon
    - also decide recording at connection time, to avoid data cut off at the beginning
    - improved UI to choose the mode
        - settings: default mode
        - long-press on device to override default mode

improve connectivity
--------------------

- experiment:
    - try to connect by uuid, see https://github.com/janosgyerik/bluetoothviewer/issues/3
        - try to connect to mac, PC, etc
    - print as much debug info as possible about remote device
    - try with different channels
- (if connectivity is improved) increase price of full app to 5 EUR
- update description

migrate to modern style
-----------------------

- replace menu with navigation drawer http://developer.android.com/training/implementing-navigation/nav-drawer.html
- use regular buttons

better handling of recording
----------------------------

- create common interface for receiving messages from devices
    - BluetoothViewer: specialized class for viewing data
    - DataRecorder: specialized class for recording data to file
        - toggled by preferences
- the implementations are independent and unaware of each other

next
----

- make it possible to connect to specific UUID
- make it possible to connect to specific channel
- transform lines
    - delete first n characters
    - delete until first occurrence of
    - perform substitution
    - prepend timestamp
    - prepend gps data
- clear button
- hexa view
- save data to files on android
- exclude lines
    - matching string
    - matching regex
- toolbar: record on/off
- files app to view recorded data

minor
-----

- reduce code duplication in xml using styles

later
-----

- make device list scrollable when too long
- remember texts recently sent to bluetooth devices
    - preset buttons
- adjustable font size
