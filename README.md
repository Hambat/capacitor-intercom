<p align="center"><br><img src="https://user-images.githubusercontent.com/236501/85893648-1c92e880-b7a8-11ea-926d-95355b8175c7.png" width="128" height="128" /></p>
<h3 align="center">Capacitor Intercom</h3>
<p align="center"><strong><code>@capacitor-community/intercom</code></strong></p>
<p align="center">
  Capacitor community plugin for enabling Intercom capabilities
</p>

<p align="center">
  <img src="https://img.shields.io/maintenance/yes/2020?style=flat-square" />
  <a href="https://www.npmjs.com/package/@capacitor-community/intercom"><img src="https://img.shields.io/npm/l/@capacitor-community/intercom?style=flat-square" /></a>
<br>
  <a href="https://www.npmjs.com/package/@capacitor-community/intercom"><img src="https://img.shields.io/npm/dw/@capacitor-community/intercom?style=flat-square" /></a>
  <a href="https://www.npmjs.com/package/@capacitor-community/intercom"><img src="https://img.shields.io/npm/v/@capacitor-community/intercom?style=flat-square" /></a>
  <!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
<a href="#contributors"><img src="https://img.shields.io/badge/all%20contributors-6-orange?style=flat-square" /></a>
<!-- ALL-CONTRIBUTORS-BADGE:END -->
</p>

## Maintainers

| Maintainer   | GitHub                                | Social                                          |
| ------------ | ------------------------------------- | ----------------------------------------------- |
| Stewan Silva | [stewwan](https://github.com/stewwan) | [@StewanSilva](https://twitter.com/StewanSilva) |

## Notice 🚀

We're starting fresh under an official org. If you were using the previous npm package `capacitor-intercom`, please update your package.json to `@capacitor-community/intercom`. Check out [changelog](/CHANGELOG.md) for more info.

## Installation

Using npm:

~~npm install @capacitor-community/intercom~~
```bash
npm install https://github.com/Fiksuruoka-fi/capacitor-intercom --save
```

Using yarn:

~~yarn add @capacitor-community/intercom~~
```bash
yarn add https://github.com/Fiksuruoka-fi/capacitor-intercom
```

Sync native files:

```bash
npx cap sync
```

## API

- registerIdentifiedUser
- registerUnidentifiedUser
- updateUser
- logout
- logEvent
- displayMessenger
- displayMessageComposer
- displayHelpCenter
- hideMessenger
- displayLauncher
- hideLauncher
- displayInAppMessages
- hideInAppMessages
- setUserHash
- setBottomPadding
- sendPushTokenToIntercom
- handlePush
- sendPushTokenToIntercom
- receivePush
- displayCarousell

## Usage

```js
import { Intercom } from "@capacitor-community/intercom";
import { PushNotifications } from "@capacitor/push-notifications";

/**
 * Register for push notifications from Intercom
 */
PushNotifications.register()

/**
 * Register an indetified user
 */
Intercom
  .registerIdentifiedUser({ userId: 123456, email: "email@domain.com" })

/**
 * Register a log event
 */
Intercom
  .logEvent({ name: "my-event", data: { pi: 3.14 } })

/**
 * Display the message composer
 */
Intercom
  .displayMessageComposer({ message: "Hello there!" } })

/**
 * Identity Verification
 * https://developers.intercom.com/installing-intercom/docs/ios-identity-verification
 */
Intercom
  .setUserHash({ hmac: "xyz" } })


/**
 * Launch mobile carousels 
 * https://www.intercom.com/help/en/articles/4605339-launch-mobile-carousels-from-a-button-in-your-mobile-app
 */
Intercom
  .displayCarousell({ id: "123456" } })


//////////////////////////////////////////////
// To receive push notifications in Android you'll have to send push token to Intercom
// and start listening received notifications in PushNotifications plugin:
//////////////////////////////////////////////

/**
 * Register Push notification listener for "registration" action
 * to send push token to Intercom in Android devices
 * 
 * Only for Android
 */
PushNotifications.addListener('registration', async ({ value }) => {
  // Send token to Intercom if platform is Android
  if (Capacitor.getPlatform() === 'android') {
    Intercom.sendPushTokenToIntercom({ value })
  }
})

/**
 * Register Push notification listener for "pushNotificationReceived" handler
 * to check if push is from Intercom and handle it their way
 * 
 * Only for Android
 */
PushNotifications.addListener('pushNotificationReceived', (notification) => {
  // Handle push received in Intercom if platform is Android and push notification is coming from Intercom
  if (Capacitor.getPlatform() === 'android') {
    Intercom.receivePush(notification.data)
  }
})
```

## iOS setup

- `ionic start my-cap-app --capacitor`
- `cd my-cap-app`
- `npm install —-save @capacitor-community/intercom`
- `mkdir www && touch www/index.html`
- `npx cap add ios`
- add intercom keys to capacitor's configuration file

```
{
 …
  "plugins": {
   "Intercom": {
      "ios-apiKey": "ios_sdk-xxx",
      "ios-appId": "yyy"
    }
  }
…
}
```

- `npx cap open ios`
- sign your app at xcode (general tab)

> Tip: every time you change a native code you may need to clean up the cache (Product > Clean build folder) and then run the app again.

## Android setup

- `ionic start my-cap-app --capacitor`
- `cd my-cap-app`
- `npm install —-save @capacitor-community/intercom`
- `mkdir www && touch www/index.html`
- `npx cap add android`
- add intercom keys to capacitor's configuration file

```
{
 …
  "plugins": {
   "Intercom": {
      "android-apiKey": "android_sdk-xxx",
      "android-appId": "yyy",
      "android-senderId": "123"
    }
  }
…
}
```

- `npx cap open android`

> Tip: every time you change a native code you may need to clean up the cache (Build > Clean Project | Build > Rebuild Project) and then run the app again.

## License

MIT

## Example

- https://github.com/capacitor-community/intercom/blob/master/example

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://twitter.com/StewanSilva"><img src="https://avatars1.githubusercontent.com/u/719763?v=4" width="75px;" alt=""/><br /><sub><b>Stew</b></sub></a><br /><a href="https://github.com/capacitor-community/intercom/commits?author=stewwan" title="Code">💻</a> <a href="https://github.com/capacitor-community/intercom/commits?author=stewwan" title="Documentation">📖</a></td>
    <td align="center"><a href="https://davidseek.com/"><img src="https://avatars2.githubusercontent.com/u/17073950?v=4" width="75px;" alt=""/><br /><sub><b>David Seek</b></sub></a><br /><a href="https://github.com/capacitor-community/intercom/commits?author=davidseek" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/rnikitin"><img src="https://avatars3.githubusercontent.com/u/1829318?v=4" width="75px;" alt=""/><br /><sub><b>Roman Nikitin</b></sub></a><br /><a href="https://github.com/capacitor-community/intercom/commits?author=rnikitin" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/atomassoni"><img src="https://avatars1.githubusercontent.com/u/17362459?v=4" width="75px;" alt=""/><br /><sub><b>Anne Tomassoni</b></sub></a><br /><a href="https://github.com/capacitor-community/intercom/commits?author=atomassoni" title="Code">💻</a> <a href="https://github.com/capacitor-community/intercom/pulls?q=is%3Apr+reviewed-by%3Aatomassoni" title="Reviewed Pull Requests">👀</a></td>
    <td align="center"><a href="https://github.com/mmodzelewski"><img src="https://avatars2.githubusercontent.com/u/7762633?v=4" width="75px;" alt=""/><br /><sub><b>Maciej Modzelewski</b></sub></a><br /><a href="https://github.com/capacitor-community/intercom/commits?author=mmodzelewski" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/spaghettiguru"><img src="https://avatars.githubusercontent.com/u/5624009?v=4" width="75px;" alt=""/><br /><sub><b>Oleg Yuzvik</b></sub></a><br /><a href="#maintenance-spaghettiguru" title="Maintenance">🚧</a></td>
  </tr>
</table>

<!-- markdownlint-enable -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!
