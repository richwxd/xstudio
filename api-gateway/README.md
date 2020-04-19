
```startuml
@startuml
class Strategy {
  {abstract} boolean check(String key, HttpServletRequest request, int times, TimeUnit unit, RedisTemplate<Object, Object> redisTemplate)
}

class IpStrategy {
  boolean check(String key, HttpServletRequest request, int times, TimeUnit unit, RedisTemplate<Object, Object> redisTemplate)
}

class TokenStrategy {
  boolean check(String key, HttpServletRequest request, int times, TimeUnit unit, RedisTemplate<Object, Object> redisTemplate)
}

class SessionStrategy {
  boolean check(String key, HttpServletRequest request, int times, TimeUnit unit, RedisTemplate<Object, Object> redisTemplate)
}

Strategy <|-- IpStrategy
Strategy <|-- TokenStrategy
Strategy <|-- SessionStrategy
@enduml
```