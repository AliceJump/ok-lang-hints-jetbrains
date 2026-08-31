# ok-script Lang Hints for JetBrains

JetBrains Platform/PyCharm port of the VS Code extension in the repository root.

## Implemented in this first version

- `self.lang.<module>.<key>` completion and quick documentation.
- OCR `match` completion/documentation from `ocr.po`.
- `fL` / `FeatureList` template completion and quick documentation.
- `EffectType.XXX` and JSON/Python effect-ID completion/documentation.
- Python inline hints for language keys, OCR patterns, and effect IDs.
- Searchable native template gallery with insert/copy/open-source actions.
- Project settings for paths, locale, aliases, and feature toggles.

## Build

Use the bundled Wrapper from this directory:

- Windows: `gradlew.bat buildPlugin`
- macOS/Linux: `./gradlew buildPlugin`

The plugin ZIP is written to `build/distributions/`.

By default the build uses `D:/IDE/PyCharm` when present. Remove or override
`platformLocalPath` in `gradle.properties` to download the configured PyCharm version.
