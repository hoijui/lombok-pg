import java.sql.Timestamp;
class DataObject {
	@lombok.Filterable
	private long dataObjectId;
	@lombok.Filterable
	private String name;
	@lombok.Filterable
	private Timestamp startDate;
	@lombok.Filterable
	private int count;
	@lombok.Filterable
	private Integer views;
	@lombok.Filterable
	private Long ticks;
	@lombok.Filterable
	private double percentage;
	@lombok.Filterable
	private Double d;
	@lombok.Filterable
	private Float fl;
	@lombok.Filterable
	private float amount;
	@lombok.Filterable
	private Boolean active;
	@lombok.Filterable
	private boolean live;
}