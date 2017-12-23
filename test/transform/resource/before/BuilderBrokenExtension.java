@lombok.Builder
class BuilderBrokenExtension {
	private final String text;
	private final int id;
	
	@lombok.BuilderExtension
	private void brokenExtension() {
		this.id = 42;
	}
}