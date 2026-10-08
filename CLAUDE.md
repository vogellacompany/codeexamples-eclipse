# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

Eclipse RCP and plug-in example projects for vogella.com tutorials.
It is a collection of independent examples, not a product, and has no tests and no CI.

## Build

Requires Java 25 and Maven 3.9.

```bash
mvn clean verify                                  # whole reactor, produces the p2 update site
mvn clean verify -pl :com.vogella.swt.widgets     # a single bundle
```

`.mvn/maven.config` adds `-B -Dtycho.baseline.replace=none`, so plain `mvn` invocations are safe.
The update site lands in `com.vogella.examples.updatesite/target/repository`.

## Structure

- Pomless Tycho 5 build: `.mvn/extensions.xml` loads `tycho-build`, so bundles, the feature and the update site have no `pom.xml` of their own and must not get one.
- The root `pom.xml` holds the module list, the Java release and the target environments (linux, win32, macosx).
- `target-platform/target-platform.target` resolves everything from the Eclipse 2026-09 release repository; a new dependency outside the listed features must be added there as a unit.
- `com.vogella.examples.feature` includes every example bundle, and `com.vogella.examples.updatesite/category.xml` publishes that feature.
- The e4 examples are PDE template applications (`Application.e4xmi`, handlers, parts) whose `plugin.xml` product extension lets them be launched from the IDE; only `com.vogella.css.fonts` also has a `.product` file, which launches it with just the bundles it needs.
- `com.vogella.rcp.perspective.plugin` and `com.vogella.rcp.perspective.addpart` contribute model fragments into `com.vogella.rcp.jface.translation`, so its element ids must stay stable.
- `org.eclipse.jface.nl1` is a fragment of `org.eclipse.jface` that supplies translated messages for the translation example.
- `com.vogella.css.fonts` targets Windows (it reads the win32 `LOGFONT` by reflection) but compiles on every platform.

## Conventions

- Bundles require `JavaSE-25` and use `jakarta.inject` / `jakarta.annotation` via `Import-Package`, never `javax.*`, since current Eclipse 4 injection ignores the javax annotations.
- New projects use the `com.vogella` prefix followed by the area they demonstrate (jface, swt, rcp, ...).
- A new project must be added as a module in the root `pom.xml`, as a plug-in in `com.vogella.examples.feature/feature.xml` and as a row in the README table.
- The repository is licensed under EPL 1.0 (`LICENSE`), and the feature's license entry matches it.
