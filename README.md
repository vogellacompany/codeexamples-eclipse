# vogella Eclipse code examples

Example projects for Eclipse RCP and plug-in development from the [vogella.com](https://www.vogella.com) website.
The bundles are built with Tycho (pomless) against the Eclipse 4.42 integration builds and published as one p2 update site.
The integration builds are needed for the SWT API used by `com.vogella.rcp.svg.currentcolor`; the target moves to the 2026-12 release once it is available.

## Examples

| Project | Shows |
| --- | --- |
| com.vogella.css.fonts | Live CSS editor showing font-weight, font-size and face resolution with the bundled Inter font (OFL), intended for Windows; start it from `css-fonts.product` |
| com.vogella.e4.model.persistence | Persisting and restoring the Eclipse 4 application model |
| com.vogella.eclipse.dndcontrols | Drag and drop of controls with SWT and Forms in an E4 part |
| com.vogella.eclipse.e4.coreexpression | Core expressions in an E4 application |
| com.vogella.jdt.quickfix | Contributing a JDT quick fix |
| com.vogella.jface.viewer | JFace viewer, including changing the text size |
| com.vogella.plugin.markers | Adding resource markers from an E4 handler |
| com.vogella.rcp.databinding | JFace data binding with converters and validation |
| com.vogella.rcp.editor.example | A simple text editor contribution |
| com.vogella.rcp.jface.translation | Translating JFace wizards at runtime |
| com.vogella.rcp.perspective.addpart | Adding a part to a perspective with a model fragment |
| com.vogella.rcp.perspective.plugin | Contributing a perspective from a plug-in |
| com.vogella.rcp.svg.currentcolor | Setting the `currentColor` of SVG icons with `SVGFileFormat.setCurrentColor` from a `-currentColor` program argument or a login dialog; start it from `svg-currentcolor.product` |
| com.vogella.swt.fonts.roboto | Loading a custom font (Roboto) in SWT |
| com.vogella.swt.widgets | Custom SWT widgets |
| org.eclipse.jface.nl1 | Fragment with JFace translations |

## Requirements

- Java 25
- Maven 3.9

## Build

```
mvn clean verify
```

The p2 update site is created in `com.vogella.examples.updatesite/target/repository` (also zipped next to it).
It contains the feature `com.vogella.examples.feature` with all example bundles.

## Import into Eclipse

Import the projects with File > Import > Existing Projects into Workspace.
Open `target-platform/target-platform.target` and click "Set as Active Target Platform".

## Naming convention

New projects use the `com.vogella` prefix followed by the area they demonstrate, for example jface, swt, rcp and the like.
Add them as a module in the root `pom.xml` and as a plug-in in `com.vogella.examples.feature/feature.xml`.
