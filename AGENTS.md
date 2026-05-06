# Project Slash Commands

When the user sends one of these project commands, run the matching `./slash` command from the repository root.

- `/ac "<message>"`: run `./slash /ac "<message>"`.
- `/push`: run `./slash /push`.
- `/switch "<branch name>"`: run `./slash /switch "<branch name>"`.
- `/new "<name>"`: run `./slash /new "<name>"`.
- `/new-scaffold "<name>"`: same as `/new`, for clients where `/new` is a built-in slash command.
- `/run`: run `./slash /run`.

The branch commands restore the original branch after they finish. The `/push` command switches to `release`, pushes `origin release`, and then restores the original branch.
