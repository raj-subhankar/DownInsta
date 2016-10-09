# Contributing

Skip to the [summary](#summary)

### First steps

Firstly, you will need a local fork of this repository. Press the 'fork' button at the top of this page.
Now that you have a fork, you will need to clone it to your computer.

Using a terminal, clone your fork: 

`git clone https://github.com/Lyxnx/DownInsta.git`

It should look something like this:

![git clone](https://github.com/Lyxnx/DownInsta/raw/master/img/git-clone.png)

_**Your fork's link will be different**_

Afterwards, you will need a new remote so you can grab upstream changes from this original repo.
To do this, you will need the link for this repo rather than your fork. 
Once you have that, execute this from a terminal:

`git remote add upstream https://github.com/raj-subhankar/DownInsta.git`

Now you should have 2 remotes for this project on your local disk:
1. _origin_ - this points to your fork of this project.
2. _upstream_ - this points to this main repo so you can grab changes. This remote is read only.

### The fun stuff

Now that you have everything set up, you can now contribute to the project. 

It might help to have your own branch that you can commit to, rather than the master branch.
If this is the case, let's assume you want to branch from the master into your own branch 
called _develop_:

```
$ git checkout master
$ git pull upstream master && git push origin master
$ git checkout -b develop
```

This will make sure you are on the master branch. It will then synchronise your local copy with 
the upstream copy.
Finally we create our branch, _develop_. It helps if the branch name is meaningful.

Now on to the fun part, I promise.
Do what you need to do, if you're fixing a bug, only fix the bug; don't do anything else like 
add a new feature.

Commit in logical blocks and make sure each commit message is meaningful and provides others a
fine outline of what it does. A **good** commit message would be 
`Fix bug that stops users from loading the application`.
A **bad** commit message would be `Fix bug` - don't do that as it does not provide any information at all.

### Create the Pull Request (PR)

Once you have your local changes, you will need to push it to your fork's _origin_ remote.
In this command we push our _develop_ branches changes to our fork's _origin_:

`git push -u origin develop`

This will create the branch on your GitHub project. The `-u` flag links this branch with the remote
one so that in the future, you can simply type `git push origin`.

Now go to your browser and navigate to your fork (https://github.com/Lyxnx/DownInsta in my case)
At the top you'll see a handy 'Pull Request' button. 

![pr button](https://github.com/Lyxnx/DownInsta/raw/master/img/pr-button.png)

Press it and you'll be taken to the pull request page.

* Ensure that the base fork points to this repository and the correct branch.
* Also make sure that the head fork also points to your repository.

![pr](https://github.com/Lyxnx/DownInsta/raw/master/img/pr.png)

Provide a good title for the pull request and explain what it does and why you have done it.

If needed, also bring up any relevant issue numbers. Use `#issue-number` to achieve this.
You will have the option to double check your pull request and edit if need be.

If you are happy, you can press the 'Create pull request' button and you are done.

The rest is on the maintainer's part now. If they choose to deny your request, they will usually say why
and what you need to do to fix it (if anything)

### Summary

If you were too lazy to read all of that, I recommend reading it all unless you already know what you are doing.

1. Fork the project and clone it locally.
2. Create an upstream branch and sync the local copy before branching.
3. Do the work, write the code or whatever you need to do.
4. Push to your fork.
5. Create the PR on GitHub.