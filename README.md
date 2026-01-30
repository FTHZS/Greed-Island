# Greed Island

An evolutionary algorithm simulation where AI agents develop survival strategies through genetic trait inheritance, probabilistic decision-making, and multi-threaded autonomous behavior.

## About

Greed Island is a survival simulation inspired by evolutionary biology and artificial life research. Twenty AI agents ("Characters") are spawned into an environment with limited resources. Each agent makes autonomous decisions about sleeping, traveling, eating, crafting, attacking, and resource gathering based on their genetic traits. Agents that survive pass their traits to offspring with small mutations, allowing successful strategies to evolve naturally over generations.

Built over Grade 11-12 (2023-2024), this project transformed me from a Java beginner to understanding advanced concepts like multi-threading, functional programming, genetic algorithms, and complex system design. Every major Java concept I know today came from solving problems in this simulation.

## The Simulation

**Core Concept:**
Each Character is an independent thread running continuously, making decisions every few milliseconds based on their current state (hunger, energy, health) and genetic traits. The environment responds to their actions - resources deplete, locations change, and other Characters react. Natural selection occurs: Characters with effective decision-making survive and reproduce, passing their traits forward.

**Genetic System:**
- Each Character has 6 genetic traits: Sleep, Travel, Eat, Give, Craft, Attack
- Traits range from -294 to +294, affecting decision probabilities
- Offspring inherit parent traits with mutations (±15 typically)
- Mutations decrease near boundaries to prevent extremes
- Special Characters (like "KING") have fixed trait overrides

**Decision-Making System:**
Characters don't follow scripted behavior - they make probabilistic choices based on:
1. **Base decision frequency** (300 by default for each action)
2. **Genetic traits** (inherited, vary per Character)
3. **Environmental influences** (hunger increases Eat probability, tiredness increases Sleep)
4. **Current status effects** (Diseased, Poisoned, Tired, Hungry, Confused)

The RarityPool algorithm combines these factors to select actions weighted by probability.

**Environment:**
- Multiple locations (Bay, Cave, Forest, Plains, etc.)
- Each location has resources (Stone, Coal, Wood, Food)
- Resources regenerate over time
- Characters can travel between locations
- Some resources are rarer than others (using RarityPool)

## Features

### Core Simulation
- **20+ autonomous AI like Characters** running in separate threads
- **Genetic evolution** with trait inheritance and mutation
- **Probabilistic decision-making** using custom RarityPool algorithm
- **Multi-threaded environment** with thread-safe resource management
- **Dynamic world state** with resource depletion and regeneration
- **Emergent behavior** from simple rules and selection pressure

### Decision System
- **6 core actions:** Sleep, Travel, Eat, Give, Craft, Attack
- **Weighted random selection** based on genetics + state + environment
- **Influence system** that modifies probabilities based on needs
- **Status effects** that alter decision-making (Hunger, Tiredness, Disease, Poison, Confusion)

### Survival Mechanics
- **Health system** (damage, healing, death)
- **Hunger system** (depletes over time, must eat to survive)
- **Energy system** (Travels drain energy, sleep restores it)
- **Inventory management** (collect resources, use items)
- **Crafting system** (combine resources to create items)

### Evolution Features
- **Trait mutation** with configurable variance
- **Bounded evolution** (traits can't exceed limits)
- **Adaptive mutation rates** (smaller mutations near boundaries)
- **Special Character exceptions** (predefined traits for testing)
- **Generation tracking** (Winners list for reproduction)

### Advanced Systems
- **Listener pattern** for event-driven behavior
- **Functional interfaces** for condition checking
- **Lambda expressions** for custom triggers
- **Abstract classes** for shared behavior
- **Interfaces** for polymorphic implementations
- **Enums** for type-safe constants (EffectType, ItemType)

## Technologies & Concepts Learned

### Concurrency & Threading
- **Multi-threading:** Each Character runs in its own thread
- **Thread safety:** AtomicInteger for health/hunger/energy
- **Synchronization:** `synchronized` methods for status updates
- **Race conditions:** Learned to avoid with proper locking
- **Concurrent collections:** Thread-safe resource access

### Object-Oriented Design
- **Polymorphism:** Characters, Items, Listeners share behavior through inheritance
- **Abstract classes:** Base classes for shared functionality
- **Interfaces:** Contracts for different system components
- **Encapsulation:** Private fields with controlled access
- **Inheritance:** Character cloning preserves structure

### Functional Programming
- **Lambda expressions:** `(x) -> x.get() < 300` for conditions
- **Functional interfaces:** Custom callback patterns
- **Method references:** Passing functions as parameters
- **Stream API:** Used for filtering and processing collections

### Data Structures
- **HashMap:** Trait storage, status tracking, influences
- **ArrayList:** Item inventories, winner tracking
- **Custom RarityPool:** Weighted random selection algorithm
- **Listener pattern:** Event-driven architecture
a
### Advanced Java Features
- **Enums:** Type-safe constants for Items, Effects, Status types
- **AtomicInteger:** Thread-safe integer operations
- **Generics:** Type-safe collections and listeners
- **Anonymous classes:** For custom listener implementations
- **Static initialization blocks:** Setup shared data

### Algorithms & Logic
- **Genetic algorithms:** Mutation, inheritance, selection
- **Weighted random selection:** RarityPool probability distribution
- **Probabilistic decision-making:** Combining multiple factors
- **Emergent behavior:** Complex patterns from simple rules

## The RarityPool Algorithm

The most important concept from this project - a weighted random selection system:
```java
class RarityPool {
    HashMap Outcomes;  // Outcome -> Probability weight
    
    void add(String outcome, int rarity) {
        Outcomes.put(outcome, (double) rarity);
    }
    
    String simulate() {
        // Total of all weights
        double total = sum(Outcomes.values());
        
        // Random point in range [0, total)
        double random = Math.random() * total;
        
        // Find which outcome this point lands in
        double current = 0;
        for (Entry entry : Outcomes) {
            current += entry.getValue();
            if (random < current) {
                return entry.getKey();
            }
        }
    }
}
```

**Why This Matters:**

This algorithm appears throughout my projects:
- **Mining Simulator:** Mineral drop rates
- **Greed Island:** Decision-making, resource spawning
- **Game design:** Any weighted random selection

It taught me that complex probability distributions can be implemented elegantly with simple math.

## Evolution in Action

**Example - The Emergence of "Sleepers":**

Early generations: Most Characters die from exhaustion (low energy)

After ~5 generations: Characters with higher "Sleep" traits survive longer

After ~10 generations: Dominant strategy becomes "Sleep when tired, eat when hungry, travel rarely"

After ~15 generations: Counter-strategy emerges - aggressive Characters with high "Attack" eliminate sleepers, take their resources

**This wasn't programmed - it emerged from selection pressure.**

## Technical Challenges Solved

### Challenge 1: Thread Safety
**Problem:** 20+ threads accessing shared resources (location inventories, Character states)

**Solution:** 
- AtomicInteger for health/hunger/energy (atomic reads/writes)
- Synchronized methods for complex state changes
- Immutable data where possible

### Challenge 2: Decision Complexity
**Problem:** How do Characters decide what to do each turn?

**Solution:** RarityPool + Trait system + Influence system
```
Final probability = Base - Trait modifier - Environmental influence

Example (Eating decision):
Base: 300
Trait (Eat): -50 (this Character doesn't like eating)
Influence (Hungry status): +100 (but they're starving)
Final: 300 - (-50) - 100 = 250

Compare to Sleep: 300 - 20 - 0 = 280
Sleep wins, Character sleeps instead of eating, dies later
```

### Challenge 3: Genetic Inheritance
**Problem:** How to mutate traits without making them too random or too static?

**Solution:** Adaptive mutation rates
```java
int mutation = randInt(2*variance) - variance;  // ±15 normally

if (trait > bound-10) {
    mutation = randInt(2*5) - 5;  // ±5 near maximum
}

// Prevents traits from overshooting limits
// Allows fine-tuning near boundaries
```

### Challenge 4: Infinite Simulation
**Problem:** Simulation runs forever, no clear "end"

**Solution:** 
- Generation tracking (Winners list)
- Menu system for pausing/inspecting state
- Time-based events (resource regeneration)
- Configurable intervals for user control

### Challenge 5: Debugging Multi-threaded Code
**Problem:** 20 threads printing simultaneously creates unreadable output

**Solution:**
- Message interval system (configurable delays)
- Message filtering (toggle types on/off)
- Menu-based inspection (pause to view state)
- Structured logging with timestamps

## Project Evolution

**Version 1-2:** Basic Character movement, simple decisions

**Version 3:** Added RarityPool for decision-making

**Version 4:** Implemented genetic traits and inheritance

**Version 5:** Added multi-threading (each Character independent)

**Version 6 (Final):** 
- Full environment system
- Crafting mechanics
- Status effects (Diseased, Poisoned, Confused)
- Menu system for runtime control
- Advanced listener patterns
- Optimized thread safety

## Code Architecture

**Core Classes:**
- `Character.java` (686 lines) - Agent behavior, decision-making, genetics
- `Greed_Island.java` (425 lines) - Main simulation loop, initialization
- `RarityPool.java` (229 lines) - Weighted random selection algorithm
- `Location.java` (186 lines) - Environment management, resources
- `Listener.java` (234 lines) - Event system, functional callbacks
- `Inventory.java` (122 lines) - Item storage and management
- `Item.java` (143 lines) - Resource definitions
- `Apple.java` (262 lines) - Food item with effects
- `Menu.java` (29 lines) - User interaction system

**Total:** 3,444 lines of original code

## What This Project Taught Me

### Before Greed Island:
- Knew basic Java syntax (variables, loops, if-statements)
- Could write simple programs
- Mining Simulator level complexity
- **Java beginner**

### After Greed Island:
- Deep understanding of multi-threading and concurrency
- Mastery of OOP principles (polymorphism, inheritance, abstraction)
- Functional programming with lambdas and interfaces
- Complex algorithm design (RarityPool, genetic algorithms)
- System architecture for emergent behavior
- **Java proficiency - ready for industry-level projects**

### Specific Learnings:

**Multi-threading:**
- How to create and manage threads
- Thread safety and synchronization
- Race conditions and how to prevent them
- AtomicInteger and volatile keywords
- When to use synchronized vs lock-free

**Object-Oriented Design:**
- When to use abstract classes vs interfaces
- How polymorphism enables extensibility
- Encapsulation for maintaining invariants
- Inheritance for code reuse without duplication

**Functional Programming:**
- Lambda expressions for concise callbacks
- Functional interfaces as contracts
- Passing behavior as parameters
- Stream operations (though limited use here)

**System Design:**
- How simple rules create complex behavior
- Emergent properties vs programmed behavior
- Feedback loops and system stability
- Balancing competing forces

**Algorithm Design:**
- Weighted random selection (RarityPool)
- Genetic algorithms (mutation, selection, inheritance)
- Probabilistic decision-making
- Optimization (performance with 20+ threads)

**Software Engineering:**
- Managing large codebase (3,444 lines)
- Iterative development (v1 → v6)
- Debugging complex systems
- Performance profiling and optimization

## Real-World Applications

**What I learned here applies to:**
- **Game AI:** Probabilistic behavior, autonomous agents
- **Simulation systems:** Physics, economics, ecology
- **Genetic algorithms:** Optimization, machine learning
- **Concurrent systems:** Web servers, distributed computing
- **Event-driven architecture:** User interfaces, real-time systems

The RarityPool concept alone has been used in every project since:
- SkynetGrid: Priority-based task selection
- Game projects: Loot drops, random events
- Any system needing weighted probability

## Running the Simulation

**Requirements:** Java 11+

**To run:**
```bash
javac Greed_Island.java
java Greed_Island
```

**Controls:**
- Simulation runs automatically
- Menu appears every N seconds (configurable)
- View alive/sleeping/dead Characters
- Inspect inventories and stats
- Adjust message intervals
- Continue or exit

**Configuration:**
- Edit `interval` in code to change menu frequency
- Edit `messageInterval` for output speed
- Modify starting Character traits in `initializeCharacters()`
- Adjust mutation variance in `Character.Clone()`

## Performance Notes

With 20 Characters running simultaneously:
- Each makes decisions ~3 times per second
- Total: ~60 decisions/second across all threads
- Resource updates: Continuous
- No noticeable lag on modern hardware

Optimizations made:
- AtomicInteger instead of synchronized getters
- Minimal locking (only for complex state changes)
- Efficient RarityPool algorithm (O(n) where n = number of choices)

## Future Improvements

- **Analytics:** Track trait evolution over generations, plot graphs
- **Advanced genetics:** Sexual reproduction (two parents), recombination
- **World builder:** Let the user create the world and fill each location with specific resources before starting simulator.
- **Cooperation mechanics:** Alliances, Betrayals, Sharing resources, teaming up

## Legacy

This project's concepts appear in all my later work:

**SkynetGrid:** Multi-threading for client connections

**Other games:** Probabilistic systems, weighted random

**System design:** Event-driven architecture, autonomous agents

**Problem-solving approach:** Break complex problems into simple rules, let complexity emerge

## Status

Core simulation complete and functional. Successfully demonstrates evolutionary emergence of survival strategies. Serves as my Java learning foundation and concept laboratory for future projects.

---

*Built by Abhinav Biju • Grade 11-12 (2023-2024)*

*From Java beginner to advanced concepts through evolutionary simulation*
