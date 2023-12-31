![](docs/images/logo.png#gh-dark-mode-only)
![](docs/images/logo_dark.png#gh-light-mode-only)

<a href="https://github.com/cuynu/MagiskDelta/releases/latest"><img src="https://img.shields.io/github/downloads/cuynu/MagiskDelta/total?label=Download" alt="Badge"></img></a>

#### This is not an officially supported Google product

## Introduction

Magisk Delta is a suite of open source software for customizing and rooting Android like official Magisk, created by HuskyDG (Nguyen Hoang The Vi) in VietNam, supporting devices higher than Android 6.0.<br>

Some highlight features:

- **MagiskSU**: Provide root access for applications
- **Magisk Modules**: Modify read-only partitions by installing modules
- **MagiskBoot**: The most complete tool for unpacking and repacking Android boot images
- **Zygisk**: Run code in every Android applications' processes
- **MagiskHide**: Hide Magisk from various form of detection
- **SuList**: Magisk only visible to be included on the sulist 
- **Core-only mode**: Only core features such as MagiskSU (root access) and MagiskHide are available and no modules will load modules will load in Core-only mode.
- **Bootloop Protection**: Reboot to core-only mode if zygote process failed to start for many times
- **Monet theme**: Android 12+ dynamic material you theme in Magisk Delta app


## Downloads

[Github](https://github.com/cuynu/MagiskDelta/releases)

## Useful Links

- [Installation Instruction](https://topjohnwu.github.io/topjohnwu/install.html)
- [Magisk Documentation](https://topjohnwu.github.io/Magisk/)

## Bug Reports

#### PLEASE ONLY REPORT ISSUE IN OFFICIAL MAGISK DELTA REPOSITORY!

**Only bug reports from Debug builds will be accepted.**

For installation issues, upload both boot image and install logs.<br>
For Magisk Delta issues, upload boot logcat or dmesg.<br>
For Magisk Delta app crashes, record and upload the logcat when the crash occurs.

## Building and Development

- Magisk Delta builds on any OS Android Studio supports. Install Android Studio and do the initial setups.
- Clone sources: `git clone --recurse-submodules https://github.com/cuynu/MagiskDelta.git`
- Install Python 3.8+ \
  (Windows only: select **'Add Python to PATH'** in installer, and run `pip install colorama` after install)
- Configure to use the JDK bundled in Android Studio:
  - Linux: `export PATH="/path/to/androidstudio/jre/bin:$PATH"`
  - Windows: Add `C:\Path\To\Android Studio\jre\bin` to environment variable `PATH`
- Set environment variable `ANDROID_SDK_ROOT` to the Android SDK folder (can be found in Android Studio settings)
- Run `./build.py ndk` to let the script download and install NDK for you
- To start building, run `build.py` to see your options. \
  For each action, use `-h` to access help (e.g. `./build.py all -h`)
- To start development, open the project with Android Studio. The IDE can be used for both app (Kotlin/Java) and native sources.
- Optionally, set custom configs with `config.prop`. A sample `config.prop.sample` is provided.

## Signing and Distribution

- The certificate of the key used to sign the final Magisk Delta APK product is also directly embedded into some executables. In release builds, Magisk's root daemon will enforce this certificate check and reject and forcefully uninstall any non-matching Magisk Delta apps to protect users from malicious and unverified Magisk Delta APKs.
- To do any development on Magisk Delta itself, switch to an **official debug build and reinstall Magisk Delta** to bypass the signature check.
- To distribute your own Magisk Delta builds signed with your own keys, set your signing configs in `config.prop`.
- Check [Google's Documentation](https://developer.android.com/studio/publish/app-signing.html#generate-key) for more details on generating your own key.

## Translation Contributions

Default string resources for the Magisk app and its stub APK are located here:

- `app/src/main/res/values/strings.xml`
- `stub/src/main/res/values/strings.xml`

Translate each and place them in the respective locations (`[module]/src/main/res/values-[lang]/strings.xml`).

## License

    Magisk Delta, including all git submodules are free software:
    you can redistribute it and/or modify it under the terms of the
    GNU General Public License as published by the Free Software Foundation,
    either version 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
