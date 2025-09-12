# Bot User Guide

Bot is a friendly desktop chatbot designed to help you keep track of your various tasks. It's optimized for use via a
Command Line Interface (CLI), so if you can type fast, you can manage your tasks much faster than traditional GUI apps!

## Quick Start

1. Ensure you have Java `17` installed on your computer.
2. Download the latest `bot.jar` file from the release page
3. Open a command terminal, `cd` into the folder you put the jar file in.
4. Run the application using the `java -jar bot.jar` command.
5. Type a command in the terminal and press Enter to execute it.
6. Refer to the [Features](#features) section below for details of each command.

---

## Features

> **Notes about the command format:**
>
> * Words in `UPPER_CASE` are the parameters to be supplied by the user. e.g., in `todo DESCRIPTION`, `DESCRIPTION` is a
    parameter which can be used as `todo Read book`.
> * Parameters must be provided in the exact order specified.
> * Extra text entered for a command that doesn't take parameters (like `list` or `bye`) will be ignored.

### Adding a Todo: `todo`

Adds a simple task with no date attached.

**Format:** `todo DESCRIPTION`

**Example:**

* `todo Buy milk`
* `todo Complete CS2103T project draft`

### Adding a Deadline: `deadline`

Adds a task that needs to be completed by a specific date/time.

**Format:** `deadline DESCRIPTION /by YYYY-MM-DD HHMM`

* The date should be in `YYYY-MM-DD` format (e.g., `2023-09-25`).
* The time is optional and should be in 24-hour `HHMM` format without a colon (e.g., `1800` for 6:00 PM).
* **Note:** Other date formats are supported. Check out the dateTime parsing function for more information

**Examples:**

* `deadline Return library book /by 2023-10-02`
* `deadline Submit report /by 2023-09-30 2359`

### Adding an Event: `event`

Adds a task that starts at a specific time and ends at a specific time.

**Format:** `event DESCRIPTION /from START_DATETIME /to END_DATETIME`

* The datetime should be in `YYYY-MM-DD HHMM` format.

**Example:**

* `event Project Meeting /from 2023-10-05 1400 /to 2023-10-05 1600`

### Listing All Tasks: `list`

Shows a numbered list of all tasks in your task manager.

**Format:** `list`

### Marking a Task as Done: `mark`

Marks a specific task in the list as completed.

**Format:** `mark INDEX`

* Marks the task at the specified `INDEX`.
* The index refers to the number shown in the displayed task list from the `list` command.
* The index **must be a positive integer** (1, 2, 3, ...).

**Example:**

* `list` followed by `mark 2` marks the 2nd task in the list as done.

### Marking a Task as Not Done: `unmark`

Marks a specific task in the list as not completed yet.

**Format:** `unmark INDEX`

* Unmarks the task at the specified `INDEX`.

**Example:**

* `unmark 1` marks the 1st task in the list as not done.

### Deleting a Task: `delete`

Permanently removes a specific task from the list.

**Format:** `delete INDEX`

* Deletes the task at the specified `INDEX`.

**Example:**

* `list` followed by `delete 3` deletes the 3rd task in the list.

### Finding Tasks: `find`

Searches your task list for tasks whose descriptions contain the given keyword(s). The search is case-insensitive.

**Format:** `find KEYWORD`

**Example:**

* `find book` will return tasks like "Return library book" and "Buy textbook".

### Exiting the Program: `bye`

Saves all your tasks to a file and exits the application.

**Format:** `bye`

---

## Command Summary

| Action           | Format, Examples                                                                                            |
|------------------|-------------------------------------------------------------------------------------------------------------|
| **Help**         | `help`                                                                                                      |
| **Add Todo**     | `todo DESCRIPTION` <br>e.g., `todo Read book`                                                               |
| **Add Deadline** | `deadline DESCRIPTION /by YYYY-MM-DD HHMM` <br>e.g., `deadline Assignment /by 2023-09-30 2359`              |
| **Add Event**    | `event DESCRIPTION /from START /to END` <br>e.g., `event Concert /from 2023-12-05 2000 /to 2023-12-05 2200` |
| **List**         | `list`                                                                                                      |
| **Mark**         | `mark INDEX` <br>e.g., `mark 2`                                                                             |
| **Unmark**       | `unmark INDEX` <br>e.g., `unmark 1`                                                                         |
| **Delete**       | `delete INDEX` <br>e.g., `delete 3`                                                                         |
| **Find**         | `find KEYWORD` <br>e.g., `find meeting`                                                                     |
| **Exit**         | `bye`                                                                                                       |

