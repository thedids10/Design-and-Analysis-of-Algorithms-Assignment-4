# Smart City / Smart Campus Scheduling — Assignment 4

**Student:** Nursultan Tursunbaev  
**Group:** SE-2402

## Goal
Consolidate algorithms for _Strongly Connected Components_ (SCC) & _Topological Ordering_, and _Shortest/Longest Paths in DAGs_ into a practical scheduling scenario. The code processes directed dependency graphs of city service tasks, including cyclic and acyclic dependencies.

---

## Contents & Structure

```
/src/
  graph/
    scc/    (SCC + condensation)
    topo/   (Topological sort)
    dagsp/  (DAG shortest/longest path)
    metrics/
    utils/  (IO/generator)
  Main.java (menu entry point)
test/java/
  graph/scc/   SCCSolverTest.java
  graph/topo/  TopoSortTest.java
  graph/dagsp/ DAGShortestPathTest.java
/data/         (Test datasets, tasks.json...)
README.md
```

---

## Features

### 1. Graph tasks

- **SCC (Tarjan):**  
  - Finds all strongly connected components for input dependency graphs (from `tasks.json` or `/data`).
  - Outputs component vertex lists and sizes.
  - Builds condensation (DAG of SCCs).

- **Topological sorting:**  
  - Kahn's algorithm and DFS-based sort of condensation graph.
  - Outputs SCC/component topo order.

- **DAG Shortest/Longest Path:**  
  - Supports **edge weights** as per `tasks.json` (see field `"w"` in edge).
  - Finds single-source shortest paths (DP over topo order).
  - Finds critical (longest) path using DP/max over topo order.
  - Reconstructs one optimal/critical path.

### 2. Dataset Generation

- 9 datasets are generated under `/data/`:
  - 3 *Small* (6–10 nodes, simple/cycles/dag)
  - 3 *Medium* (10–20 nodes, mixed SCC/DAG)
  - 3 *Large* (20–50 nodes, performance/time tests)
- Files use the same format as `tasks.json`:

```json
{
  "directed": true,
  "n": 8,
  "edges": [
    {"u": 0, "v": 1, "w": 3},
    ...
  ],
  "source": 4,
  "weight_model": "edge"
}
```

---

## Instrumentation

- **Metrics interface**: tracks DFS calls/edges (SCC), queue operations (Kahn), relaxations (DAG-SP), and timings (`System.nanoTime()`).
- Output results and performance after every main task.
- Javadoc for public classes; key algorithms are commented.

---

## Tests

- **JUnit 5** tests in `src/test/java/graph/...`
  - SCC edge-cases (cycle, DAG, isolated, empty, single vertex)
  - Topo sorting + error on cycle
  - Shortest/longest paths in tiny DAG + path reconstruction

**How to run:**
- In IntelliJ IDEA: Right click `/test/java` → Run 'All Tests'.
- Or via Maven/Gradle if attached as a build/test dependency.

---

## Build & Run Instructions

### Compile (from project root):
```sh
javac -d out $(find src -name "*.java")
```
### Launch menu:
```sh
java -cp out graph.Main
```
### Generate datasets (menu 4 or directly):
```sh
java -cp out graph.utils.DatasetGenerator
```
### Run tests:
- In IDEA: Right click test folder, choose 'Run Tests'
- (Or via Maven if applicable; make sure to add JUnit 5 to dependencies)

---

## Data & Reporting

- Datasets in `/data` include variants for small/medium/large graphs and document cyclic/acyclic nature in their content/filename.
- Each solution outputs the key metrics and timings in stdout (copy-paste into your report if needed).
- For full reporting, run algorithms on each dataset and copy collected metrics into tables in your PDF/README.

---

## Implementation choices

- **Edge weights** are used for path costs (see `tasks.json` and generator).
- **All outputs** are to stdout for grading; can be easily redirected to file if necessary.

---

## License

MIT (for academic/non-commercial use).