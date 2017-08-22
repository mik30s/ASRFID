### Documentation for RFID Tag Memory Banks

![Original Memory Layout](https://user-images.githubusercontent.com/3809183/28844626-e25af138-76ca-11e7-8c87-712c954d9ddc.jpg)

#### Reserved Memory
Stores Kill and Access Passwords which are both 32bits wide.
A Kill password will permanently kill a tag i.e you wont be able to use that tag again.
An Access password will allow you to lock or unlock the tag's write capabilities.

#### Electronic Product Code (EPC) Memory
This memory bank stores the electronic product code for the tag. It has a minimum of 96 bits for
use. This is what is mostly used for tagging.

#### Tag Identification Memory
This is a readonly memory bank that holds the tag's ID which is originally written by the
manufacturer.

#### User Memory
This is a writable memory bank that can be used by applications that need more than 96bits
allowed in the EPC. You can use this if you need to store more information on that tag
like a time stamp.