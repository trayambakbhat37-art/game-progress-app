package com.example.data.repository

import com.example.data.model.CommentEntity
import com.example.data.model.GameProgressEntity
import com.example.data.model.GameTrickEntity

object SeedData {
    val initialGames = listOf(
        GameProgressEntity(
            gameId = "fall_guys",
            gameTitle = "Fall Guys: Ultimate Knockout",
            tagline = "Chaotic obstacle race & party battle royale!",
            genre = "Party Arcade / Obstacle Race",
            currentStage = "Round 3: Volleyfall & Slime Climb",
            difficulty = "Casual & Fun",
            description = "Sprint through wild obstacle courses, dodge rolling wrecking balls, survive rising pink slime, and grab the crown before everyone else!",
            keyMechanics = "Mid-Air Diving (+20% forward reach)|Ledge Grab Hold & Pull-up|Body Blocking & Momentum Transfer|Camera Pitch Centering",
            recommendedTips = "Press Dive right as you reach the apex of your jump to avoid ragdoll bounce|On Volleyfall, defend the center circle and use dive-headbonk to spike the ball into corners|In rising slime rounds, wait 0.5s at narrow choke points so crowded beans push you forward, not off",
            accentColorHex = "#FF2A85",
            totalKnownLevels = 24,
            clearedCount = 8,
            isCurrentActive = true,
            isFavorite = true
        ),
        GameProgressEntity(
            gameId = "zelda_totk",
            gameTitle = "Zelda: Tears of the Kingdom",
            tagline = "Sky islands, endless shrines & Ultrahand crafting",
            genre = "Action-Adventure Sandbox",
            currentStage = "Wellspring Island - Water Temple",
            difficulty = "Medium",
            description = "Explore Hyrule above and below with Ultrahand vehicle creation and physics-defying powers.",
            keyMechanics = "Ultrahand Weapon & Object Fusion|Ascend Ceiling Passing|Recall Time Rewind|Bullet-Time Bow Aiming",
            recommendedTips = "Attach Opals or Splash Fruit to multishot arrows to vaporize sludge lakes instantly|Use low-gravity physics on Wellspring Island to glide across full chasms without vehicles",
            accentColorHex = "#00E5FF",
            totalKnownLevels = 28,
            clearedCount = 12,
            isCurrentActive = false,
            isFavorite = true
        ),
        GameProgressEntity(
            gameId = "elden_ring",
            gameTitle = "Elden Ring",
            tagline = "The Lands Between action RPG epic",
            genre = "Action RPG / Soulslike",
            currentStage = "Stormveil Castle - Castleward Tunnel",
            difficulty = "Hardcore",
            description = "Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring.",
            keyMechanics = "Posture Breaking & Stagger|Buckler Active Parry Frames|Bloodhound Step & Quick Dodge|Ashes of War Pairing",
            recommendedTips = "Use Margit's Shackle twice in Phase 1 to freeze the boss for 6 seconds of free charged attacks|Bleed (Blood Grease) procs percentage HP damage against nearly all early demi-gods",
            accentColorHex = "#FFC72C",
            totalKnownLevels = 32,
            clearedCount = 6,
            isCurrentActive = false,
            isFavorite = true
        ),
        GameProgressEntity(
            gameId = "hollow_knight",
            gameTitle = "Hollow Knight",
            tagline = "Metroidvania kingdom of bugs and secrets",
            genre = "Metroidvania / Soulslike",
            currentStage = "White Palace - Path of Pain",
            difficulty = "Nightmare",
            description = "Descend into the forgotten bug kingdom of Hallownest and uncover the source of the infection.",
            keyMechanics = "Nail Down-Pogo Bounce|Soul Healing Recovery|Shade Cloak Invincibility Dash|Charm Synergy Pairing",
            recommendedTips = "Pair Hiveblood charm with Grubsong for infinite passive health regen between sawblade gauntlets|Down-slash moving sawblades at their highest point to reset your double jump",
            accentColorHex = "#8B5CF6",
            totalKnownLevels = 18,
            clearedCount = 5,
            isCurrentActive = false,
            isFavorite = false
        ),
        GameProgressEntity(
            gameId = "cyberpunk_2077",
            gameTitle = "Cyberpunk 2077",
            tagline = "Night City dystopian mercenary RPG",
            genre = "Open World / Action RPG",
            currentStage = "Act 1 Finale - Konpeki Plaza",
            difficulty = "Medium",
            description = "Become a cyber-enhanced mercenary in the dangerous megalopolis of Night City.",
            keyMechanics = "Sandevistan Bullet Time|Quickhack RAM Infiltration|Dash & Air-Dash Mobility|Critical Melee Dismemberment",
            recommendedTips = "Before leaping off the penthouse balcony, run upstairs to Yorinobu's helipad AV for the iconic Satori katana|Grab the pet iguana egg inside the bedroom terrarium before escaping",
            accentColorHex = "#FF6D00",
            totalKnownLevels = 20,
            clearedCount = 7,
            isCurrentActive = false,
            isFavorite = false
        )
    )

    val initialTricks = listOf(
        GameTrickEntity(
            id = 1,
            gameId = "fall_guys",
            gameTitle = "Fall Guys: Ultimate Knockout",
            levelName = "Round 3: Volleyfall Match",
            category = "Level Walkthrough",
            difficulty = "Easy",
            title = "Volleyfall: Center Court Spikes & Zero-Bounce Defense",
            summary = "Control the center floor circle and use headbonk jump-dives to spike balls into the opponent's unguardable corners.",
            steps = listOf(
                "Position your bean right inside the circular center shadow marker on your court.",
                "Never run under the ball while it's falling; wait for the ball shadow to align 1 step in front of you.",
                "Jump straight up and press Dive at the peak of your bounce to smash the ball directly downward into their back court.",
                "If the ball drops near the net, jump against the net barrier to trigger an instant upward ricochet."
            ),
            proTips = listOf(
                "Diving right before making contact transfers 40% more velocity than a standard walk-into-ball.",
                "If an opponent tries to grab you near the net, jump-dive forward to push them into the hazard zone."
            ),
            authorName = "BeanChampion_01",
            authorRank = "Crown Hoarder",
            upvotes = 428,
            isUpvoted = true,
            isSaved = true,
            isLevelCleared = true,
            estimatedMinutes = 3,
            commentsCount = 6
        ),
        GameTrickEntity(
            id = 2,
            gameId = "fall_guys",
            gameTitle = "Fall Guys: Ultimate Knockout",
            levelName = "Slime Climb Obstacle Gauntlet",
            category = "Shortcut",
            difficulty = "Medium",
            title = "Slime Climb: 3 Secret Shortcut Jumps & Cylinder Skip",
            summary = "Bypass the congested starting stampede and save 15 seconds by jumping the yellow bouncy cylinder and triangular ramp.",
            steps = listOf(
                "Starting Turn: Jump onto the first yellow bouncy triangle on the left to catapult straight to the 2nd floor platform.",
                "Push Block Section: Run along the far-right edge where the push blocks have a 0.5-second longer delay.",
                "Yellow Cylinder Bridge: Dive straight onto the center seam of the cylinder; do not walk or you will slip.",
                "Final Spinning Poles: Jump in the direction the pole is spinning so momentum throws you forward toward the finish line."
            ),
            proTips = listOf(
                "Never grab other beans on the narrow conveyor belt—it slows both of you down and the rising slime will catch you.",
                "Keep camera tilted slightly top-down so you can spot falling punching gloves early."
            ),
            authorName = "SpeedyBean",
            authorRank = "Pro Speedrunner",
            upvotes = 512,
            isUpvoted = false,
            isSaved = true,
            isLevelCleared = true,
            estimatedMinutes = 4,
            commentsCount = 8
        ),
        GameTrickEntity(
            id = 3,
            gameId = "fall_guys",
            gameTitle = "Fall Guys: Ultimate Knockout",
            levelName = "Tip Toe Fake Tile Maze",
            category = "Puzzle",
            difficulty = "Medium",
            title = "Tip Toe: Spot Fake Shaking Tiles & The 2-Tile Leap",
            summary = "How to safely spot genuine floating tiles without being pushed off, plus the winning dive-leap into the finish portal.",
            steps = listOf(
                "Look closely at the tiles: Fake tiles will subtly wobble and shake every 3 seconds even without beans stepping on them.",
                "Stay in the second row of the pack; let eager beans test the frontier while you stay securely on confirmed solid tiles.",
                "Once you see the final path reaching within 2 tiles of the finish line, jump and immediately DIVE across the gap to qualify first!"
            ),
            proTips = listOf(
                "The last row of tiles right before the finish line is ALWAYS solid if the preceding tile was solid.",
                "If someone grabs you near the edge, jump immediately to break the grapple."
            ),
            authorName = "CrownTactician",
            authorRank = "Grandmaster Tactician",
            upvotes = 389,
            isUpvoted = false,
            isSaved = false,
            isLevelCleared = false,
            estimatedMinutes = 2,
            commentsCount = 5
        ),
        GameTrickEntity(
            id = 4,
            gameId = "fall_guys",
            gameTitle = "Fall Guys: Ultimate Knockout",
            levelName = "Final Round: Hex-A-Gone",
            category = "Boss Guide",
            difficulty = "Hard",
            title = "Hex-A-Gone: Slow 1-Tile Hopping Technique for 3x Time",
            summary = "Don't run across tiles! Master the single-hop method to make each hexagon last 1.2 seconds, outlasting all sprinters.",
            steps = listOf(
                "Start of round: Jump onto an isolated outer perimeter cluster away from the aggressive sprinters.",
                "Hop on a tile, wait until it flashes red, and jump to the adjacent tile at the very last microsecond.",
                "Check the lower floor shadow below you before dropping so you land on a wide untouched platform.",
                "On the bottom slime floor, dive to cut off the final opponent's bridge and claim the crown."
            ),
            proTips = listOf(
                "Slow hopping gives you over 45 seconds on a single layer, while sprinting burns the layer in 12 seconds."
            ),
            authorName = "HexMaster_X",
            authorRank = "Crown Hoarder",
            upvotes = 670,
            isUpvoted = true,
            isSaved = true,
            isLevelCleared = true,
            estimatedMinutes = 4,
            commentsCount = 11
        ),
        GameTrickEntity(
            id = 5,
            gameId = "zelda_totk",
            gameTitle = "Zelda: Tears of the Kingdom",
            levelName = "Wellspring Island - Water Temple",
            category = "Level Walkthrough",
            difficulty = "Easy",
            title = "Water Temple Clear in 6 Mins: Opal Arrow AoE & Ascend Skip",
            summary = "Speed up the Water Temple faucet puzzles and Mucktorok boss fight using Opal arrow combinations and low-gravity physics.",
            steps = listOf(
                "Fuse an Opal to your bow arrows or use Splash Fruit with Multishot bow to instantly clear wide patches of sludge.",
                "Use the low-gravity jumping mechanics to glide directly across the gaps instead of activating the floating water globes.",
                "For the water wheels: Use Zonai Hydrants attached to a simple rotating wheel to continuously clear sludge.",
                "Boss Phase 1: Activate Sidon's water shield attack to shoot a water wave through Mucktorok's shark form, instantly exposing the small octopus.",
                "Boss Phase 2: When he sprays sludge geysers, shoot Keese Eyeball homing water arrows while mid-air in bullet time."
            ),
            proTips = listOf(
                "Keep a Magic Rod fused with an Opal for an infinite durability sludge-clearing water cannon.",
                "Zora Armor lets you swim up waterfalls effortlessly to reach upper levels in seconds."
            ),
            authorName = "ZonaiArchitect",
            authorRank = "Master Engineer",
            upvotes = 289,
            isUpvoted = false,
            isSaved = true,
            isLevelCleared = true,
            estimatedMinutes = 6,
            commentsCount = 3
        ),
        GameTrickEntity(
            id = 6,
            gameId = "elden_ring",
            gameTitle = "Elden Ring",
            levelName = "Stormveil Castle - Castleward Tunnel",
            category = "Boss Guide",
            difficulty = "Medium",
            title = "Margit the Fell Omen: Parrying & Shackle Stagger Strat",
            summary = "Defeat Margit without over-leveling using Margit's Shackle and buckler parrying.",
            steps = listOf(
                "Purchase Margit's Shackle from Patches in Murkwater Cave for 5,000 runes beforehand.",
                "Equip a Small Shield or Buckler with 'Buckler Parry' skill for maximum active frames.",
                "Phase 1: Use the Shackle twice right as he steps forward to pin him for 6 seconds of free heavy attacks.",
                "Phase 2: When he conjures the glowing golden hammer, sprint directly under his leap towards his back to safely execute a charged heavy attack."
            ),
            proTips = listOf(
                "Summon Sorcerer Rogier outside the fog gate; he draws aggro reliably in Phase 1.",
                "Grease your weapon with Bleed (Blood Grease) to proc massive percentage damage."
            ),
            authorName = "ValkyrieTarnished",
            authorRank = "Grandmaster Tactician",
            upvotes = 342,
            isUpvoted = false,
            isSaved = false,
            isLevelCleared = false,
            estimatedMinutes = 8,
            commentsCount = 4
        )
    )

    val initialComments = listOf(
        CommentEntity(
            id = 1,
            trickId = 1,
            authorName = "PinkBean42",
            authorRank = "Party Legend",
            content = "The center court jump-dive spike won me 3 crowns in a row today! Best tip ever.",
            likes = 54,
            isLiked = true
        ),
        CommentEntity(
            id = 2,
            trickId = 2,
            authorName = "SlimeSurvivor",
            authorRank = "Pro Runner",
            content = "Yellow cylinder dive skip works 100% of the time once you aim for the center line.",
            likes = 39,
            isLiked = false
        ),
        CommentEntity(
            id = 3,
            trickId = 4,
            authorName = "TileHopper",
            authorRank = "Crown Hoarder",
            content = "Slow hopping is literally game-changing. Everyone below falls into the slime while you're still on level 3!",
            likes = 82,
            isLiked = true
        )
    )
}
