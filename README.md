#image-processing-monolith
System that uses machine learning algorithms to test various aspects of performance - CPU usage, waiting time, memory consumption, cashe usage, idle time, heap space etc.
I've build the same application in two versions. One according to the newest standards of building microservices and the other is a monolith.
Through series of tests with various stress level I compared the two mentioned architectures.

My main hypothesis is that contrary to the idealized microservices, there are multiple scenarios in which monolith architecture would be way more performant, cheaper and should be chosen.
Hypothesis was confirmed through series of stress tests and saved on dozens charts.

This is a monolith system version implemented for the purpose of master's degree thesis examination.
