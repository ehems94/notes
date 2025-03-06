> Micrometer 1.10


- logging
- metrics
- distributed tracing
## Implementation
### 1 - Interface
```kotlin
interface TeaService {
	fun truc()
}
```
### 2 - Business logic
```kotlin
class DefaultTeaService(private val dep1: Dep1) : TeaService {
	override fun truc(param1, param2) { }
}
```
### 3 - Observability logic

```kotlin
class ObservedTeaService(
	private val delegate: DefaultTeaService,
	private val observationRegistry: ObservationRegistry
) : TeaService {
	
	override fun truc(param1: Int, param2: Int) {
		return Observation.createNotStarted("make.tea")
			.lowCardinalityKeyValue("param1", param1)
			.lowCardinalityKeyValue("param2", param2)
			.observe { delegate.truc(param1, param2) }
	}
	
}
```
### 4 - Bean
```kotlin
class Config {
	@Bean
	fun teaService(dep1: Dep1, observationRegistry: ObservationRegistry): TeaService {
		return ObservedTeaService(DefaultTeaService(dep1), observationRegistry)
	}
}
```