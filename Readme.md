<img align="right" height="100" src="https://raw.githubusercontent.com/midhunhk/artwork/master/C19Counter/export/app_icon.png" />

# c19-counter-app
[![Release](https://img.shields.io/github/release/midhunhk/c19-counter-app.svg)](https://github.com/midhunhk/c19-counter-app/releases)

An informational android app to get C-19 status for the places you want to check easily on your mobile device.
This app presents the data as is from third party sources without any enrichment.
Please refer to official government sources for accurate data.

## Backend API
The back end API for this project is located at  
https://github.com/midhunhk/c19-web-api  
which sources the results from third party services

## Screenshots
<img alt="Home" src="https://github.com/midhunhk/artwork/blob/master/C19Counter/export/screen-shots/Screenshot_1600612808.png?raw=true" width="200"/> <img alt="Add a Place" src="https://raw.githubusercontent.com/midhunhk/artwork/master/C19Counter/export/screen-shots/Screenshot_1600612815.png" width="200"/> <img alt="Results" src="https://raw.githubusercontent.com/midhunhk/artwork/master/C19Counter/export/screen-shots/Screenshot_1600612834.png" width="200"/> <img alt="About" src="https://raw.githubusercontent.com/midhunhk/artwork/master/C19Counter/export/screen-shots/Screenshot_1600612841.png" width="200"/>

## Learnings
 - Integration with web API
 - Improve lib-aeapps
 - Latest ViewModel and LiveData implementation
 - Room database
 - Android bundle archival mode
 - Create custom full screen dialog fragments
 - Custom toolbar interactions
 - Swipe to Pull Refresh (Surprisingly easy to implement)

## Not available on the Play Store
This app is not available on the Google Play Store. Please find the signed apk from the releases page.
We believe this app was rejected after Google Play review as the data are sourced from third party sources and they want to prevent
in-consistent information from being available to the public.

## How to use
Only a few Countries and Indian states have been added to the dropdown menu in the current state. If you need to view the data for other places, 
update the "countries.xml" and "india_states.xml" files, with the place names and the two letter code for each.

## License
MIT License

Copyright (c) 2020 Midhun Harikumar

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.g

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
