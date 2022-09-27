# Space Shooter
#### Author: Daniel Peña López

## **Completed pass achievements**
- Refactor all literals: Config class in util package contains all game play settings
- Refactor all user-facing Strings into resources: string.xml contains now all user-facing Strings
- Improve the Star entity class: Color is now randomized and there are 3 possible star shapes which
  you can see in the Shapes enum. Moreover star speed is now proportional to its size (the bigger,
  the faster)
- Add sound effects to all important interactions: start game, collision, game over and boost have 
  now its corresponding sound effect
- Implement recovery frames: when the main character is collides with an enemy, it blinks for a 
  BLINKING_PERIOD during which it cannot lose lives even if it hits an enemy. The blinking behaviour
  is reached via 2 Timers: One that makes the player "unbeatable" during the BLINKING_PERIOD and
  another one that uses BLINKING_ACTIVE and BLINKING_INACTIVE periods to create the blinking itself
  and make the user aware of the situation. All period values can be found in the Config class in
  the util package.
- Move UI / HUD rendering from Game into a separate class: You can find the next class in the util
package

## **Completed pass with distinction achievements**
- Give all enemies movement patterns: The enemies can go straight with a randomized speed 
  or with a randomized sin movement which makes the game way more unpredictable
- Add music background with MediaPlayer
- Save and display a high score list (eg. multiple values), instead of a single high score: You
can see the list of Records with by clicking in the button of the main screen "See List of Records"
  The name has associated a player Name which you can change in the Settings screen which you can
  access in the main screen too. All of this was achieved with a Room Database and Dependency
  injection. The playerName is saved with SharedPreferences

