import java.sql.Timestamp;
class DataObject {
  private @lombok.Filterable long dataObjectId;
  private @lombok.Filterable String name;
  private @lombok.Filterable Timestamp startDate;
  private @lombok.Filterable int count;
  private @lombok.Filterable Integer views;
  private @lombok.Filterable Long ticks;
  private @lombok.Filterable double percentage;
  private @lombok.Filterable Double d;
  private @lombok.Filterable Float fl;
  private @lombok.Filterable float amount;
  private @lombok.Filterable Boolean active;
  private @lombok.Filterable boolean live;
  DataObject() {
    super();
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> dataObjectIdEquals(final long _dataObjectId) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.dataObjectId == _dataObjectId);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> dataObjectIdGreaterThan(final long _dataObjectId) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.dataObjectId > _dataObjectId);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> dataObjectIdGreaterThanEqual(final long _dataObjectId) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.dataObjectId >= _dataObjectId);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> dataObjectIdLessThan(final long _dataObjectId) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.dataObjectId < _dataObjectId);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> dataObjectIdLessThanEqual(final long _dataObjectId) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.dataObjectId <= _dataObjectId);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, java.lang.Long> dataObjectId() {
    return new lombok.Functions.Function1<DataObject, java.lang.Long>() {
  x() {
    super();
  }
  public @java.lang.Override java.lang.Long apply(final DataObject item) {
    return item.dataObjectId;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> nameEquals(final String _name) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.name.equals(_name);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> nameContains(final String _name) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.name.contains(_name);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> nameStartsWith(final String _name) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.name.startsWith(_name);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> nameEndsWith(final String _name) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.name.endsWith(_name);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, String> name() {
    return new lombok.Functions.Function1<DataObject, String>() {
  x() {
    super();
  }
  public @java.lang.Override String apply(final DataObject item) {
    return item.name;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> startDateEquals(final Timestamp _startDate) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.startDate.equals(_startDate);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> startDateGreaterThan(final Timestamp _startDate) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.startDate.after(_startDate);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> startDateGreaterThanEqual(final Timestamp _startDate) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.startDate.after(_startDate) || item.startDate.equals(_startDate));
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> startDateLessThan(final Timestamp _startDate) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.startDate.before(_startDate);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> startDateLessThanEqual(final Timestamp _startDate) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.startDate.before(_startDate) || item.startDate.equals(_startDate));
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, Timestamp> startDate() {
    return new lombok.Functions.Function1<DataObject, Timestamp>() {
  x() {
    super();
  }
  public @java.lang.Override Timestamp apply(final DataObject item) {
    return item.startDate;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> countEquals(final int _count) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.count == _count);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> countGreaterThan(final int _count) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.count > _count);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> countGreaterThanEqual(final int _count) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.count >= _count);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> countLessThan(final int _count) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.count < _count);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> countLessThanEqual(final int _count) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.count <= _count);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, java.lang.Integer> count() {
    return new lombok.Functions.Function1<DataObject, java.lang.Integer>() {
  x() {
    super();
  }
  public @java.lang.Override java.lang.Integer apply(final DataObject item) {
    return item.count;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> viewsEquals(final Integer _views) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.views.equals(_views);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> viewsGreaterThan(final Integer _views) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.views > _views);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> viewsGreaterThanEqual(final Integer _views) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.views >= _views);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> viewsLessThan(final Integer _views) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.views < _views);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> viewsLessThanEqual(final Integer _views) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.views <= _views);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, Integer> views() {
    return new lombok.Functions.Function1<DataObject, Integer>() {
  x() {
    super();
  }
  public @java.lang.Override Integer apply(final DataObject item) {
    return item.views;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> ticksEquals(final Long _ticks) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.ticks.equals(_ticks);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> ticksGreaterThan(final Long _ticks) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.ticks > _ticks);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> ticksGreaterThanEqual(final Long _ticks) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.ticks >= _ticks);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> ticksLessThan(final Long _ticks) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.ticks < _ticks);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> ticksLessThanEqual(final Long _ticks) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.ticks <= _ticks);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, Long> ticks() {
    return new lombok.Functions.Function1<DataObject, Long>() {
  x() {
    super();
  }
  public @java.lang.Override Long apply(final DataObject item) {
    return item.ticks;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> percentageEquals(final double _percentage) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.percentage == _percentage);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> percentageGreaterThan(final double _percentage) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.percentage > _percentage);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> percentageGreaterThanEqual(final double _percentage) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.percentage >= _percentage);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> percentageLessThan(final double _percentage) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.percentage < _percentage);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> percentageLessThanEqual(final double _percentage) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.percentage <= _percentage);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, java.lang.Double> percentage() {
    return new lombok.Functions.Function1<DataObject, java.lang.Double>() {
  x() {
    super();
  }
  public @java.lang.Override java.lang.Double apply(final DataObject item) {
    return item.percentage;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> dEquals(final Double _d) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.d.equals(_d);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> dGreaterThan(final Double _d) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.d > _d);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> dGreaterThanEqual(final Double _d) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.d >= _d);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> dLessThan(final Double _d) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.d < _d);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> dLessThanEqual(final Double _d) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.d <= _d);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, Double> d() {
    return new lombok.Functions.Function1<DataObject, Double>() {
  x() {
    super();
  }
  public @java.lang.Override Double apply(final DataObject item) {
    return item.d;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> flEquals(final Float _fl) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.fl.equals(_fl);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> flGreaterThan(final Float _fl) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.fl > _fl);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> flGreaterThanEqual(final Float _fl) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.fl >= _fl);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> flLessThan(final Float _fl) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.fl < _fl);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> flLessThanEqual(final Float _fl) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.fl <= _fl);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, Float> fl() {
    return new lombok.Functions.Function1<DataObject, Float>() {
  x() {
    super();
  }
  public @java.lang.Override Float apply(final DataObject item) {
    return item.fl;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> amountEquals(final float _amount) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.amount == _amount);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> amountGreaterThan(final float _amount) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.amount > _amount);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> amountGreaterThanEqual(final float _amount) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.amount >= _amount);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> amountLessThan(final float _amount) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.amount < _amount);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> amountLessThanEqual(final float _amount) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.amount <= _amount);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, java.lang.Float> amount() {
    return new lombok.Functions.Function1<DataObject, java.lang.Float>() {
  x() {
    super();
  }
  public @java.lang.Override java.lang.Float apply(final DataObject item) {
    return item.amount;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> activeEquals(final Boolean _active) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.active.equals(_active);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> activeIsTrue() {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.active;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> activeIsFalse() {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (! item.active);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, Boolean> active() {
    return new lombok.Functions.Function1<DataObject, Boolean>() {
  x() {
    super();
  }
  public @java.lang.Override Boolean apply(final DataObject item) {
    return item.active;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> liveEquals(final boolean _live) {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (item.live == _live);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> liveIsTrue() {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return item.live;
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Predicates.Predicate1<DataObject> liveIsFalse() {
    return new lombok.Predicates.Predicate1<DataObject>() {
  x() {
    super();
  }
  public @java.lang.Override boolean evaluate(final DataObject item) {
    return (! item.live);
  }
};
  }
  public static @java.lang.SuppressWarnings("all") lombok.Functions.Function1<DataObject, java.lang.Boolean> live() {
    return new lombok.Functions.Function1<DataObject, java.lang.Boolean>() {
  x() {
    super();
  }
  public @java.lang.Override java.lang.Boolean apply(final DataObject item) {
    return item.live;
  }
};
  }
}