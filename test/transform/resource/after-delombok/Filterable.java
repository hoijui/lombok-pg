import java.sql.Timestamp;

class DataObject {
	
	private long dataObjectId;
	private String name;
	private Timestamp startDate;
	private int count;
	private Integer views;
	private Long ticks;
	private double percentage;
	private Double d;
	private Float fl;
	private float amount;
	private Boolean active;
	private boolean live;
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> dataObjectIdEquals(final long _dataObjectId) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.dataObjectId == _dataObjectId;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> dataObjectIdGreaterThan(final long _dataObjectId) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.dataObjectId > _dataObjectId;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> dataObjectIdGreaterThanEqual(final long _dataObjectId) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.dataObjectId >= _dataObjectId;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> dataObjectIdLessThan(final long _dataObjectId) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.dataObjectId < _dataObjectId;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> dataObjectIdLessThanEqual(final long _dataObjectId) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.dataObjectId <= _dataObjectId;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, java.lang.Long> dataObjectId() {
		return new lombok.Functions.Function1<DataObject, java.lang.Long>(){
			
			
			@java.lang.Override
			public java.lang.Long apply(final DataObject item) {
				return item.dataObjectId;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> nameEquals(final String _name) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.name.equals(_name);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> nameContains(final String _name) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.name.contains(_name);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> nameStartsWith(final String _name) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.name.startsWith(_name);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> nameEndsWith(final String _name) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.name.endsWith(_name);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, String> name() {
		return new lombok.Functions.Function1<DataObject, String>(){
			
			
			@java.lang.Override
			public String apply(final DataObject item) {
				return item.name;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> startDateEquals(final Timestamp _startDate) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.startDate.equals(_startDate);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> startDateGreaterThan(final Timestamp _startDate) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.startDate.after(_startDate);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> startDateGreaterThanEqual(final Timestamp _startDate) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.startDate.after(_startDate) || item.startDate.equals(_startDate);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> startDateLessThan(final Timestamp _startDate) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.startDate.before(_startDate);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> startDateLessThanEqual(final Timestamp _startDate) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.startDate.before(_startDate) || item.startDate.equals(_startDate);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, Timestamp> startDate() {
		return new lombok.Functions.Function1<DataObject, Timestamp>(){
			
			
			@java.lang.Override
			public Timestamp apply(final DataObject item) {
				return item.startDate;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> countEquals(final int _count) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.count == _count;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> countGreaterThan(final int _count) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.count > _count;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> countGreaterThanEqual(final int _count) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.count >= _count;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> countLessThan(final int _count) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.count < _count;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> countLessThanEqual(final int _count) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.count <= _count;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, java.lang.Integer> count() {
		return new lombok.Functions.Function1<DataObject, java.lang.Integer>(){
			
			
			@java.lang.Override
			public java.lang.Integer apply(final DataObject item) {
				return item.count;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> viewsEquals(final Integer _views) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.views.equals(_views);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> viewsGreaterThan(final Integer _views) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.views > _views;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> viewsGreaterThanEqual(final Integer _views) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.views >= _views;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> viewsLessThan(final Integer _views) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.views < _views;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> viewsLessThanEqual(final Integer _views) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.views <= _views;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, Integer> views() {
		return new lombok.Functions.Function1<DataObject, Integer>(){
			
			
			@java.lang.Override
			public Integer apply(final DataObject item) {
				return item.views;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> ticksEquals(final Long _ticks) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.ticks.equals(_ticks);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> ticksGreaterThan(final Long _ticks) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.ticks > _ticks;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> ticksGreaterThanEqual(final Long _ticks) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.ticks >= _ticks;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> ticksLessThan(final Long _ticks) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.ticks < _ticks;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> ticksLessThanEqual(final Long _ticks) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.ticks <= _ticks;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, Long> ticks() {
		return new lombok.Functions.Function1<DataObject, Long>(){
			
			
			@java.lang.Override
			public Long apply(final DataObject item) {
				return item.ticks;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> percentageEquals(final double _percentage) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.percentage == _percentage;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> percentageGreaterThan(final double _percentage) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.percentage > _percentage;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> percentageGreaterThanEqual(final double _percentage) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.percentage >= _percentage;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> percentageLessThan(final double _percentage) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.percentage < _percentage;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> percentageLessThanEqual(final double _percentage) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.percentage <= _percentage;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, java.lang.Double> percentage() {
		return new lombok.Functions.Function1<DataObject, java.lang.Double>(){
			
			
			@java.lang.Override
			public java.lang.Double apply(final DataObject item) {
				return item.percentage;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> dEquals(final Double _d) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.d.equals(_d);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> dGreaterThan(final Double _d) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.d > _d;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> dGreaterThanEqual(final Double _d) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.d >= _d;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> dLessThan(final Double _d) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.d < _d;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> dLessThanEqual(final Double _d) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.d <= _d;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, Double> d() {
		return new lombok.Functions.Function1<DataObject, Double>(){
			
			
			@java.lang.Override
			public Double apply(final DataObject item) {
				return item.d;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> flEquals(final Float _fl) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.fl.equals(_fl);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> flGreaterThan(final Float _fl) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.fl > _fl;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> flGreaterThanEqual(final Float _fl) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.fl >= _fl;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> flLessThan(final Float _fl) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.fl < _fl;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> flLessThanEqual(final Float _fl) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.fl <= _fl;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, Float> fl() {
		return new lombok.Functions.Function1<DataObject, Float>(){
			
			
			@java.lang.Override
			public Float apply(final DataObject item) {
				return item.fl;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> amountEquals(final float _amount) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.amount == _amount;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> amountGreaterThan(final float _amount) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.amount > _amount;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> amountGreaterThanEqual(final float _amount) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.amount >= _amount;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> amountLessThan(final float _amount) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.amount < _amount;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> amountLessThanEqual(final float _amount) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.amount <= _amount;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, java.lang.Float> amount() {
		return new lombok.Functions.Function1<DataObject, java.lang.Float>(){
			
			
			@java.lang.Override
			public java.lang.Float apply(final DataObject item) {
				return item.amount;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> activeEquals(final Boolean _active) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.active.equals(_active);
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> activeIsTrue() {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.active;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> activeIsFalse() {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return !item.active;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, Boolean> active() {
		return new lombok.Functions.Function1<DataObject, Boolean>(){
			
			
			@java.lang.Override
			public Boolean apply(final DataObject item) {
				return item.active;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> liveEquals(final boolean _live) {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.live == _live;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> liveIsTrue() {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return item.live;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Predicates.Predicate1<DataObject> liveIsFalse() {
		return new lombok.Predicates.Predicate1<DataObject>(){
			
			
			@java.lang.Override
			public boolean evaluate(final DataObject item) {
				return !item.live;
			}
		};
	}
	
	@java.lang.SuppressWarnings("all")
	public static lombok.Functions.Function1<DataObject, java.lang.Boolean> live() {
		return new lombok.Functions.Function1<DataObject, java.lang.Boolean>(){
			
			
			@java.lang.Override
			public java.lang.Boolean apply(final DataObject item) {
				return item.live;
			}
		};
	}
}