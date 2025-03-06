> Micrometer 1.10

- logging
- metrics
- distributed tracing
## Loki
## Elastic
1. `build.gradle.kts`
```kotlin
implementation("co.elastic.apm:elastic-apm-agent:1.39.0")
```
2. Configuration
```properties
elastic.apm.server_urls=http://apm-server:8200
elastic.apm.service_name=my-spring-app elastic.apm.environment=production
elastic.apm.application_packages=com.mydomain
```
3. Deployment Kubernetes
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-spring-app
spec:
  template:
    spec:
      containers:
        - name: my-spring-app
          image: my-spring-app:latest
          env:
            - name: JAVA_TOOL_OPTIONS
              value: "-javaagent:/app/elastic-apm-agent.jar -Delastic.apm.service_name=my-spring-app -Delastic.apm.server_urls=http://apm-server:8200"
          volumeMounts:
            - name: apm-agent
              mountPath: /app/elastic-apm-agent.jar
              subPath: elastic-apm-agent.jar
      volumes:
        - name: apm-agent
          configMap:
            name: apm-agent-config

```
3. Code
```java
import co.elastic.apm.api.CaptureTransaction;
import co.elastic.apm.api.CaptureSpan;

@Service public class MyService {
	@CaptureTransaction("custom-transaction")
	public void processRequest() { someMethod(); }
	
	@CaptureSpan("custom-span")
	private void someMethod() { // Some business logic }
}
```
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