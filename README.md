# ReadMe first for the project information

# LRU - Least Recently Used
* Least Recently Used is a popular implementation for caching techniques
* The idea is to see the elements in memory and remove the least recently used
* Least Recently Used in simple terms put is
  * The oldest one or
  * The one that is not used recently


# Two Implementations
  ## Synchronized Implementation
    * The implementation uses SynchronizedMap  
    * When the map is created, its accessMode is set to true
    * This causes the map to be re-rendered when get function is called making the map LRU
  ## ReentrantLock Implementation
    * The implmentation uses ReentrantReadWriteLock 
    * When the map is created, its accessMode is set to false which is default value
    * Map does not re-rendered when get function is called making the map FIFO
    * This means that, the map ordering remains the same even after the get function is called and when you enter the next element, the first element is deleted as map is FIFO

# Demonstration with Example
  ## There are two files created with http endpoints listed in them
    ### test_cache.http
      * The file tests the CacheController which implements LRU
    ### test_reentrant_cache.http
      * The file tets the ReentrantLockCacheController which implements LRU via ReentrantLock
        ** To make the test LRU, set accessMode to true
        ** To make the test FIFO, set accessMode to false
  