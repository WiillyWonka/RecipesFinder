Git - workflow.

1) Any project code is placed in the release branch.

2) A project participant creates a feature branch (branched from release) to implement new components and functionalities. The semantics of a particular feature of a branch are defined after "feature /" by a noun or concise phrase.

3) At the end of his task, the participant takes the current code from the release branch for protection against code conflicts - the git pull or git merge command. Resolves conflicts and makes a Pull request to the main release branch.

4) Upon completion of the project, the code of the release branch merges with master. 