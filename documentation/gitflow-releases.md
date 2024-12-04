# How to create a new Release

For this project I will be using the `Semantic Versioning`.
This uses the format`MAJOR.MINOR.PATCH`. For example: `v1.2.3`

- Meaning of every number:
  - MAJOR: Incompatible changes that break the API, or its previous functionality.
  - MINOR: New retro-compatible functionalities.
  - PATCH: Bug fixes and minor adjustments.

## Create a new release

1. Execute the command `git flow relese start vX.X.X`.
2. Perform adjustments and verifications in the release branch.
    - Correction of pending errors.
    - Update of versions in the configuration files.
    - Final changes IN the documentation, like a `CHANGELOG`.
3. Finalize the release with `git flow release finish vX.X.X`.
4. Publish remote tag with `git push origin --tags`

## Documentation of Changelogs

Separate changes by category:

```markdown
# Changelog

## [1.1.1] - 2024-12-02
### Added
- New functionality for user management.

### Fixed
- Error handling exceptions on `/api/orders`.

### Changed
- Database performance improvements.
```