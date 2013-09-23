package gob.sis.simos.entity;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="working_day")
public class Jornada {

	@DatabaseField(columnName="id",id=true)
	private Integer id;
	@DatabaseField(columnName="user_id")
	private String userId;
	@DatabaseField(columnName="day_string")
	private String dayString;
	@DatabaseField(columnName="start_day")
	private Date start;
	@DatabaseField(columnName="finish_day")
	private Date finish;
	@DatabaseField(columnName="hhmmss")
	private String timeString;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDayString() {
		return dayString;
	}
	public void setDayString(String dayString) {
		this.dayString = dayString;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getFinish() {
		return finish;
	}
	public void setFinish(Date finish) {
		this.finish = finish;
	}
	public String getTimeString() {
		return timeString;
	}
	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
