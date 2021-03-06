# TaskBook User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `taskbook.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/showColours.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : Lists all active tasks 
   * **`add`**` Homework by 24 sep 6pm`: 
     adds a task named `Homework` to the Task Master
   * **`delete`**` 212` : deletes the task with ID 212 shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>

## Features

<!-- @@author A0141064U -->
The general command structure is to type the `command key` + other details.

**Colour Scheme**

<img src="images/showPriority.png" width="600"><br>

purple: high

blue: medium

green: low

<img src="images/showStatus.png" width="600"><br>

red: expired

ignore: grey

done: black


The tasks are sorted as follows: 

1) By status 
* Active and Expired tasks are at the top,
* Done and Ignored are at the bottom

2) By pin
* pinned tasks are at the top

3) By priority
* tasks with higher priority are at the top

4) By time
* tasks will be sorted with the earlier date at the top


**Adding tasks**

<img src="images/add.png" width="600"><br>

Task Book supports highly flexible command format. The user can enter command in any order and Task Book intelligently identifies the contents based on keywords and natural language processing.

For example, 
To add a task, type `add` + `taskname` + `from start time` + ` by end time` + `#task_priority` + `#tags` + `@venue`  + `pin status`

* Example: user can type `add` `play soccer` `from tomorrow 2pm` `to 6pm` `#high` `#sport` `@Utown` ` #pin`

<!-- @@author A0139958H -->
This same command can be written in any order. Other alternatives

* Example: user can type `add` `from tomorrow 2pm` `to 6pm` `play soccer` `@Utown` ` #pin` `#high` `#sport`  
* Example: user can type `add` `@Utown` `play soccer`  `from tomorrow 2pm` `to 6pm` `#sport` ` #pin` `#high` 

In addition to that, Task Book also supports parameter within parameter. For example,

* Example: user can type `add` `success party from 6pm to 8pm for completing CS2103 project @soc #party #high #pin`

Notice how the start and end dates are contained within the task name. Task Book is able to intelligently separate task name from other parameters. So, for the above example

`Task Name: success party for completing CS2103`

`Start date: 6pm`

`End date: 6pm`

`Venue: soc`

`Priority: high`

`Status: active`

`Pin: true`

`Tags: party`

<!-- @@author A0141064U -->
Only the command key and taskname are compulsory; other fields are optional and it would take default or null values if not entered.

<!-- @@author A0139958H -->
**Parameters**

`Task Name`: Task names should be AlphaNumeric.

`Date`: Start date has to be followed by the keyword `from`. End date has to be followed by the keyword `by`. If the command contains start date and end date, then alternatively, `from` and `to` can also be used to denote the dates respectively. All keywords are case insensitive.

`Note:` Although `from`, `by`, `to` are keywords for the dates, they can be used in task name as well. Task Book is able to intelligently identify if they mean date or name.

Task Book uses Natty Natural language processing Date parser to identify dates in the command.

<!-- @@author A0141064U -->
Dates can be any of the following formats 

`formal dates (02/28/1979)`

`relaxed dates (oct 1st)`

`relative dates (tomorrow, the day before next thursday)`

`and even date alternatives (next wed or thurs)`

`time`: The above date formats may be prefixed or suffixed with time information. 

`Eg: 0600h, 06:00 hours, 6pm, 5:30 a.m., 5, 12:59, 23:59, noon, midnight`

Read more about [Natty Date Parser](http://natty.joestelmach.com/)

`priority`: Will be `medium` by default. The priority can be of `low`, `medium`, `high`. All keywords are case-insensitive.

`#`: tags. The tags cannot be `high`, `low`, `medium`, `unpin`, `pin` and `null`. These are reserved keywords for other parameters.

`@`: venue. It is also possible to concatenate venues by using `@` as a prefix for the venue names.

`Pin`: indicates whether the task should be pinned.

<!-- @@author A0139958H -->
`Status`: This is set by Task Book itself when a new task is added. The default value is `Active`. If the task has and end date, then after the end date, Task Book will update the status of task to `Expired` automatically. The user can only set the task status to be `done` or `ignore`.


**Input Validation**

Task Book parses the user input and validates it before performing the requested operation. If it found to be an invalid command, it will feedback the user.

Task Name:

`Task name is a compulsory field`

`Task name should be AlphaNumeric and not null.`

Dates:

`Dates should be an upcoming date.`

`Start date and End date should not be same.`

` There should not be multiple start dates in the input.`

` There should not be multiple end dates in the input.`

` Start Date should be before End Date.`

Tags:

` tag names should be AlphaNumeric and not null.`

` #null is a reserved keyword for removing start date or end date from an existing task.`

` cannot have duplicate tags with the same name`

Status:

` Task status can only be updated to Done or Ignore. It is not possible to set to Active or Expired. These are set by TaskBook itself.`

Venue:

` venue can't be null`

If any of the above conditions are violated, Task Book will not perform the operation and will feedback the user to make the necessary changes.

Furthermore, Task Book also checks for Date clashes with other dates. 

For example, if there's an existing task `from 4pm to 7pm` and a new task to be added anywhere in between `4pm to 7pm`, then Task Book will not add the task and will feedback the user of the date clash.

Task Book feedback the Task name, start date and end date of the task that the new task clashes with. The format is as below.

`The Start Date and End date clashes with another task 'task name' from 'start date' to 'end date'`

However, Task Book allows overlapping of tasks. So, for example, a new task can be added `2pm to 5pm` or `from 6pm to 8pm`. This is to give the users some flexibility in their options. 

<!-- @@author A0141064U -->
<img src="images/addFail.png" width="600"><br>

**Updating tasks**

<img src="images/update.png" width="800"><br>


To update a task type `update` (index of task in the list or name of task) (changes)
* Eg: Typing `update 1 by 12 dec` will change the **deadline** of the first task to 12 December 2016
* Eg: Typing `update 2 @Casa` will change the **venue** of the second task to Casa.
* Eg: Typing `update 1 #high` will change the **task priority** to high
* Eg: Typing `update 1 #unpin` will **unpin** the task

Multiple fields can be updated in a single command
* Eg: Typing `update 2 #high @home` changes the priority of task 2 to high and change the venue to home

<!-- @@author A0139958H -->

Fields can also be updated to be empty
* Eg: Typing `update 3 @null` removes the venue of the third task in the list. 
* Eg: Typing `update 3 from #null by #null @null` removes the start date, end date and venue of the third task in the list.

Tags can be added/removed as well.
* Eg: Typing `update 3 #sports #healthy` will remove the tags `#sports` and `#healthy` if found. If not, it will add the tags.

Task Book validates all the user input before performing the update operation. 

<!-- @@author A0141064U -->
**Deleting tasks**

To `delete` a task on the list that is on the screen, type `delete` [ index of task in the list] 
* eg `delete 1 `

`delete` can also be done for multiple tasks
* Eg: `delete 1 2 3` deletes tasks 1,2 and 3.
* Eg: `delete 29 1 7` deletes tasks 29, 1 and 7.

The order of index doesn't matter. 
All the index have to be valid index found in the list. Task Book will feedback the user if it founds to be an invalid index.

**Undo and Redo:**

To undo/redo the latest change to taskbook, simply type `undo`/`redo`.

Actions that can be undone/redone are **adding tasks**, **deleting tasks**, **updating tasks** and **setting task status** 

Undo and Redo also works for deleting multiple tasks.

Undo and Redo can be done multiple times.

**Updating the status of task**

<img src="images/set.png" width="800"><br>


Typing `set` `index` `new setting` updates the status of the task

User can change the task status to 

`ignore`: Eg type `set` + `2` + `ignore` causes task 2 to be ignored

`done`: Eg type `set` + ` 1` + `done` will set the settings of task 1 to be done

All the index number have to be valid index found in the list. Task Book will feedback the user if it founds to be an invalid index.

**Listing tasks**

Executing the command `list` will result in a complete list of tasks sorted by priority. The tasks that are pinned will be at the top of the list.

**Finding tasks** 

<img src="images/find.png" width="800"><br>

Task Book can filter the tasks according to the following fields: 
*note that in each field, `keyword` does not need to be fully spelt out:

eg `find` + `play` will return all tasks names that contains `play`
*ie `playground`, `playstation`, `play piano`, `role play`

the keyword of task names 
*by typing `find` + `keyword`

the venue
*by typing `find`+ `@` + `keyword`
*keyword is a venue

the tags
*by typing `find` + `#` + `keyword`
*keyword is a tag that exists

the priority
*by typing `find` + `#` + `keyword`
*the keyword can either be `high`, `medium`, `low`

the status
*by typing `find` + `#` + `keyword`

<!-- @@author A0138301U -->    

**Map**

<img src="images/map.png" width="800"><br>

Typing `map` `index` opens a window showing a Google Maps search for the task's venue.

Eg: type `map 1` to run search on Google Maps for the venue of task 1.

Note: Map function is catered for usage in Singapore only. Hence, results shown are within Singapore.

<!-- @@author A0141064U -->    


**Creating shortkeys**

<img src="images/shortcut.png" width="400"><br>

To change a shortkey for the command, type `shortcut` + `{the field you are changing}` + `{shortkey you want to initialise}` eg: ``shortcut` `add` `a` changes the shortkey for the command `add` to `a`. The next time you want to execute 'add' to add 'running' to your tasklist, simply type `a running`.

Shortkeys can be set for `add`, `delete` and `list` commands. 

This feature is only available for **Add**, **Delete** and **List**. 

**Moving taskbook to another storage location** 

<img src="images/move.png" width="800"><br>

To save the taskbook in another file place, type `file` + `new storage location` that you want to move taskbook to. 

eg `move \dropbox\mytask` will keep taskbook in a folder called 'dropbox', which will be named 'mytask.xml'.``

<!--@@author-->

**Help**

<img src="images/help.png" width="800"><br>


This command opens the user guide of TaskBook in the browser.

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.

**Q**: How do i get started using TaskBook?<br>
**A**: Type 'help' or any incorrect command will bring you to the help screen.

<!-- @@author A0139958H-->       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME ...`
Add | `add TASK_NAME by DATETIME @VENUE #PRIORITY #PIN #TAG1 #TAG2...`
Add | `add TASK_NAME from DATETIME to DATETIME @VENUE #PRIORITY #PIN #TAG1 #TAG2...`
Add | `add from DATETIME to DATETIME TASK_NAME @VENUE #PRIORITY #PIN #TAG1 #TAG2...`
Add | `add TASK_NAME from DATETIME to DATETIME TASK_NAME @VENUE @VENUE #PRIORITY #PIN #TAG1 #TAG2...`
Add | `add TASK_NAME from DATETIME TASK_NAME @VENUE @VENUE #PRIORITY #PIN #TAG1 #TAG2...`
Update | `update TASK_ID from DATETIME to DATETIME ...`
Update | `update TASK_ID by DATETIME ...`
Update | `update TASK_ID from #null to #null ...`
Update | `update TASK_ID TASK_NAME from #null to #null @VENUE #TAG1 #PIN #PRIORITY...`
Update | `update TASK_ID by #null TASK_NAME @null #TAG1 #PIN #PRIORITY...`
Delete | `delete TASK_ID`
Delete | `delete TASK_ID5 TASK_ID3 TASK_ID1 TASK_ID7 TASK_ID31 TASK_ID42 TASK_ID43`
Set | `set TASK_ID done`
Set | `set TASK_ID ignore`
Map | `map TASK_ID`
Undo | `undo`
Redo | `redo`
Find | `find KEY_WORDS`
Find | `find @VENUE`
Find | `find #TAG`
Find | `find #STATUS`
Find | `find #PRIORITY`
Select | `select TASK_ID`
Shortcut | `shortcut add KEY_WORD`
Clear | `clear`
Change directory | `file FILE_PATH`
Help | `help`
Exit | `exit`

-----
