# lombok-pg

This version of _lombok-pg_ is a collection of extensions to the original [lombok-pg](https://github.com/peichhorn/lombok-pg).

For further information about the extensions and how to use them, please visit the [ds-bean](https://github.com/Doctusoft/ds-bean) home page.


## Building

You need Oracle JDK 7!  
JDK 6 and older, or JDK 8 and later will not work!

To compile the jar files:

	ant dist


## Extensions to lombok found in this version:

#### Annotations:

- `@Action` [info](https://github.com/peichhorn/lombok-pg/wiki/%40Action)
- `@AutoGenMethodStub` [info](https://github.com/peichhorn/lombok-pg/wiki/%40AutoGenMethodStub)
- `@BoundPropertySupport` and `@BoundSetter` [info](https://github.com/peichhorn/lombok-pg/wiki/%40BoundPropertySupport-%40BoundSetter)
- `@Builder` and `@Builder.Extension` [info](https://github.com/peichhorn/lombok-pg/wiki/%40Builder-%40Builder.Extension)
- `@Cloneable` [info](https://github.com/redundent/lombok-pg-wiki/@Cloneable)
- `@DoPrivileged` [info](https://github.com/peichhorn/lombok-pg/wiki/%40DoPrivileged)
- `@EnumId` [info](https://github.com/peichhorn/lombok-pg/wiki/%40EnumId)
- `@ExtensionMethod` [info](https://github.com/peichhorn/lombok-pg/wiki/%40ExtensionMethod)
- `@Filterable` [info](https://github.com/redundent/lombok-pg/wiki/%40Filterable)
- `@FluentSetter` [info](https://github.com/peichhorn/lombok-pg/wiki/%40FluentSetter)
- `@Function` [info](https://github.com/peichhorn/lombok-pg/wiki/%40Function)
- `@LazyGetter` [info](https://github.com/peichhorn/lombok-pg/wiki/%40LazyGetter)
- `@ListenerSupport` [info](https://github.com/peichhorn/lombok-pg/wiki/%40ListenerSupport)
- `@WriteLock` and `@ReadLock` [info](https://github.com/peichhorn/lombok-pg/wiki/%40WriteLock-%40ReadLock)
- `@Await`, `@Signal` and `@AwaitBeforeAndSignalAfter` [info](https://github.com/peichhorn/lombok-pg/wiki/%40Await-%40Signal-%40SignalBeforeAwaitAfter)
- `@Predicate` [info](https://github.com/peichhorn/lombok-pg/wiki/%40Predicate)
- `@Rethrow` and `@Rethrows` [info](https://github.com/peichhorn/lombok-pg/wiki/%40Rethrow-%40Rethrows)
- `@Sanitize.Normalize` and `@Sanitize.With` [info](https://github.com/peichhorn/lombok-pg/wiki/%40Sanitize)
- `@Singleton` [info](https://github.com/peichhorn/lombok-pg/wiki/%40Singleton)
- `@SwingInvokeLater` and `@SwingInvokeAndWait` [info] (https://github.com/peichhorn/lombok-pg/wiki/%40SwingInvokeLater-%40SwingInvokeAndWait)
- `@Validate.NotEmpty`, `@Validate.NotNull` and `@Validate.With` [info](https://github.com/peichhorn/lombok-pg/wiki/%40Validate)
- `@VisibleForTesting` [info](https://github.com/peichhorn/lombok-pg/wiki/%40VisibleForTesting)
- `@Warning` [info](https://github.com/peichhorn/lombok-pg/wiki/%40Warning)
- `@XmlSerializable` [info](https://github.com/redundent/lombok-pg/wiki/%40XmlSerializable)

#### Interfaces:

- `Application` and `JVMAgent` [info](https://github.com/peichhorn/lombok-pg/wiki/Application-JVMAgent)

#### Methods:

- `tuple(expr1, expr2, ...)` [info](https://github.com/peichhorn/lombok-pg/wiki/Tupel)
- `yield(object)` [info](https://github.com/peichhorn/lombok-pg/wiki/Yield)


#### Base annotations from lombok:

- `@AllArgsConstructor, @RequiredArgsConstructor and @NoArgsConstructor` [info](http://projectlombok.org/features/Constructor.html)
- `@Cleanup` [info](http://projectlombok.org/features/Cleanup.html)
- `@Delegate` [info](http://projectlombok.org/features/Delegate.html)
- `@EqualsAndHashcode` [info](http://projectlombok.org/features/EqualsAndHashCode.html)
- `@Getter/Setter` [info](http://projectlombok.org/features/GetterSetter.html)
- `@Getter(lazy=true)` [info](http://projectlombok.org/features/GetterLazy.html)
- `@Log` [info](http://projectlombok.org/features/Log.html)
- `@SneakyThrows` [info](http://projectlombok.org/features/SneakyThrows.html)
- `@Synchronized` [info](http://projectlombok.org/features/Synchronized.html)
- `@ToString` [info](http://projectlombok.org/features/ToString.html)


#### Base methods from lombok:

- `val` [info](http://projectlombok.org/features/val.html)

## Grab the latest version:

[Download page](https://github.com/peichhorn/lombok-pg/wiki/Grab-the-latest-version)

## Documentation:
- [Check out lombok features](http://projectlombok.org/features/)
- [Check out original lombok-pg features](https://github.com/peichhorn/lombok-pg)
