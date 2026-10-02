# ok-script Lang Hints for JetBrains

> [!IMPORTANT]
> **此仓库已停止维护，准备归档。 / This repository is retired and will be archived.**
>
> JetBrains 版本的后续开发已迁移至 **[AliceJump/ok-script-toolkit-jetbrains](https://github.com/AliceJump/ok-script-toolkit-jetbrains)**，并与主仓 **[AliceJump/ok-script-toolkit](https://github.com/AliceJump/ok-script-toolkit)** 协同维护。
>
> Development has moved to **[AliceJump/ok-script-toolkit-jetbrains](https://github.com/AliceJump/ok-script-toolkit-jetbrains)**, coordinated with **[AliceJump/ok-script-toolkit](https://github.com/AliceJump/ok-script-toolkit)**.
>
> 本仓库仅保留历史代码与迁移记录，不再接受功能开发，也不会再发布 JetBrains Marketplace 更新。下面的内容仅作为历史参考。
>
> This repository is kept only for historical source and migration reference. It no longer receives feature development or publishes JetBrains Marketplace updates. The documentation below is historical.


JetBrains Platform/PyCharm port of the VS Code extension in the repository root.

## Implemented features

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

The default build downloads the configured PyCharm SDK. To reuse a local installation,
pass `-PplatformLocalPath=/absolute/path/to/PyCharm` and run Gradle with JDK 21.

## Releases

The repository root project is the single release coordinator. The version in this
repository must match the parent `package.json`, and only a new `vX.Y.Z` tag pushed to
`AliceJump/ok-lang-hints` publishes both the VS Code and JetBrains distributions.
This repository's own CI validates source changes but does not publish releases.
