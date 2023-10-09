# ![netscan@2x](https://github.com/gustavobarbosab/netscan/assets/11272342/7878c286-759f-40f6-8b4d-ddd1f48e4954)


![GitHub repo size](https://img.shields.io/github/repo-size/gustavobarbosab/netscan?style=for-the-badgee)
![GitHub language count](https://img.shields.io/github/languages/count/gustavobarbosab/netscan?style=for-the-badgee)
![GitHub forks](https://img.shields.io/github/forks/gustavobarbosab/netscan?style=for-the-badgee)
![GitHub open issues](https://img.shields.io/bitbucket/issues/gustavobarbosab/netscan?style=for-the-badgee)
![GitHub open pull requests](https://img.shields.io/bitbucket/pr-raw/gustavobarbosab/netscan?style=for-the-badgee)

## Last stable version: ![stable version](https://img.shields.io/github/v/release/gustavobarbosab/netscan?include_prereleases&style=flat-square)

## What is NetScan?

The project's goal is to create a set of tools to help developers implement applications easily, this library includes the most recent patterns and tools in the Android framework. 
Bellow we can see some tools present in this project:

- Port scan.
- Find a device by IP (Ping).
- Find devices inside a domestic network.
- Verify internet connection.
- List Wifi nearby.
- Internet connection speed.

## How to use NetScan?

### First step:
To use the library, include the JitPack repository URL inside the file `settings.gradle`, like the example below:
```
dependencyResolutionManagement {
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
If the approach above does not work or your project is configured in other way, update the file `build.gradle` like that:
```
allprojects {
    repositories {
	  ...
	  maven { url 'https://jitpack.io' }
    }
}
```

### Second step:
Include the library dependency inside your project `build.gradle`:

```
dependencies {
    implementation `com.github.gustavobarbosab:netscan:VERSAO'
}
```

## Become a contributor <br>

Do you wanna be part of this project? Please, do a repository fork and submit your PR.

## 📝 License

This project has licenses. Look at the file [LICENÇA](LICENSE.md) for more details.


## Usage examples:

<img src="https://github.com/gustavobarbosab/netscan/assets/11272342/a7035941-fb9b-4fec-af0b-9133f91d4f86" alt="review" width="200" align="right" hspace="20">

🚧 &nbsp;In Progress 🚧 

The app is being developed and it has the objective to exemplify the tools usage.

Some libraries used in the project:
- [X] View Binding
- [X] Koin
- [X] Coroutines
- [X] ViewModel
- [X] Modular features
- [ ] Mockk
- [ ] JUnit





