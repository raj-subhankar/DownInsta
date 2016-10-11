# Contributing

This guide will show you the steps of forking a repository, creating a branch and 
pushing your changes to the main repo.

If you don't want to read through all of this, you can skip straight to the summary. I recommend
reading through this however unless you have done this sort of thing many times before.

Skip to the [summary](#summary)

### Copying the repo to your computer

Press the 'fork' button at the top of the project page.
Now that you have a fork, you will need to clone it to your computer.

Using a terminal, clone your fork: 

```sh
$ git clone https://github.com/Lyxnx/DownInsta.git
```

_**Your fork's link will be different**_

Change directory into the new project folder.
Now you will need the upstream remote so you can get updates from the main repo:

```sh
$ git remote add upstream https://github.com/raj-subhankar/DownInsta.git
```

### Creating a branch

You will need a branch so when you make a pull request, the owner can see what the changes are.
This can be accomplished by doing the following, 'using feature/readme-contributors' as our branch:

```sh
$ git checkout master
$ git pull upstream master && git push origin master
$ git checkout -b feature/readme-contributors
```

### Create the Pull Request (PR)

When it is time to get your changes into the main repository, you will need to send a pull request.

```sh
$ git push -u origin feature/readme-contributors
```

Once this is done, go to your forked repository and click the 'Pull request' button.

### Summary

1. Fork the project and clone it locally.
2. Create an upstream branch and sync the local copy before branching.
3. Do the work, write the code or whatever you need to do.
4. Push to your fork.
5. Create the PR on GitHub.