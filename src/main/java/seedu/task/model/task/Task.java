package seedu.task.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.ModelManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;

//@@author A0139958H

/**
 * Represents a Task in the taskBook. Guarantees: details are present and not
 * null, field values are validated.
 */
public class Task implements ReadOnlyTask, Cloneable {

	private Name name;
	private DateTime startDate;
	private DateTime endDate;
	private Venue venue;
	private Status status = Status.ACTIVE;
	private TaskPriority priority = TaskPriority.MEDIUM; // Default priority is medium
	private PinTask pinTask = PinTask.UNPIN; // Default is unpin
	private UniqueTagList tags;

    public static final String DATE_CLASH_MESSAGE = "The Start Date and End date clashes with another task '%s' from %s to %s";
	
	/**
	 * Only Name, Priority and Status Should not be null
	 */
	public Task(Name name, DateTime startDate, DateTime endDate, Venue venue, TaskPriority priority, Status status,
	        PinTask pinTask, UniqueTagList tags) {
		assert !CollectionUtil.isAnyNull(name, priority, status);
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setVenue(venue);
		this.priority = priority;
		this.status = status;
		this.pinTask = pinTask;
		this.tags = new UniqueTagList(tags);
	}

	/**
	 * Copy constructor.
	 */
	public Task(ReadOnlyTask source) {
		this(source.getName(), source.getStartDate(), source.getEndDate(), source.getVenue(), source.getPriority(),
		        source.getStatus(), source.getPinTask(), source.getTags());
	}

	/**
	 * Empty Task Constructor.
	 */
	public Task() {
		this.startDate = new DateTime("");
		this.endDate = new DateTime("");
		this.venue = new Venue("");
		this.tags = new UniqueTagList();
	}

	/**
	 * Checks if there's a date clash between this task and the given task
	 * @param Task Object to be checked
	 * @return: Boolean true or false
	 */
	public boolean checkDateClash(Task task) {
		if (!task.getStartDate().value.isEmpty() && !task.getEndDate().value.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss zzz yyyy");

			LocalDateTime startDate = LocalDateTime.parse(this.startDate.value, formatter);
			LocalDateTime endDate = LocalDateTime.parse(this.endDate.value, formatter);
			LocalDateTime taskStartDate = LocalDateTime.parse(task.startDate.value, formatter);
			LocalDateTime taskEndDate = LocalDateTime.parse(task.endDate.value, formatter);

			LogsCenter.getLogger(ModelManager.class).info("startDate: " + startDate + ", endDate: " + endDate
			        + ", taskStartDate: " + taskStartDate + ", taskEndDate: " + taskEndDate);

			if (startDate.compareTo(taskStartDate) >= 0 && endDate.compareTo(taskEndDate) <= 0)
				return true;
		}

		return false;
	}
	
	public String dateClashMsg() {
		return String.format(DATE_CLASH_MESSAGE, this.getName(), this.getStartDate(), this.getEndDate());
	}

	/**
	 * Updates the task status to expired if the current date is after the end date
	 */
	public void updateTaskStatus() {
		if (!this.getEndDate().value.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss zzz yyyy");
			LocalDateTime endDate = LocalDateTime.parse(this.getEndDate().value, formatter);
			if (endDate.isBefore(LocalDateTime.now()))
				this.setStatus(Status.EXPIRED);
		}
	}

	@Override
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@Override
	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	@Override
	public UniqueTagList getTags() {
		return new UniqueTagList(tags);
	}

	/**
	 * Replaces this task's tags with the tags in the argument tag list.
	 */
	public void setTags(UniqueTagList replacement) {
		tags.setTags(replacement);
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
		        || (other instanceof ReadOnlyTask // instanceof handles nulls
		                && this.isSameStateAs((ReadOnlyTask) other));
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getStartDate(), getEndDate(), getVenue(), getPriority(), getStatus(), getTags());
	}

	@Override
	public String toString() {
		return getAsText();
	}

	@Override
	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	@Override
	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	@Override
	public PinTask getPinTask() {
		return pinTask;
	}

	public void setPinTask(PinTask pinTask) {
		this.pinTask = pinTask;
	}

	public void addTag(Tag tag) throws DuplicateTagException {
		this.tags.add(tag);
	}

	public void updateTag(Tag tag) {
		try {
			this.tags.add(tag);
		} catch (DuplicateTagException e) {
			this.tags.remove(tag);
		}
	}

	public Task clone() {
		try {
			this.setTags(getTags().clone());
			return (Task) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
