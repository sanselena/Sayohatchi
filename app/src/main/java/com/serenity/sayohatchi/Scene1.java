package com.serenity.sayohatchi;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class Scene1 extends AppCompatActivity {
    private TextView dialogueText;
    private RelativeLayout dialogueOptionsLayout;
    private Button option1, option2;
    private EditText nameInput;
    private String playerName = "Player"; // Default name
    private String[] dialogues = {

            "...", //0
            "[i]*W-where am i?*[/i]",
            "[i]*Am i... dreaming?*[/i]",
            "'Hello! Anyone?'",
            "[i]*...*[/i]",
            "'He-'",
            "Howdy!", // cass_neutral appears
            "'GAH! You scared me!'",
            "Hahahah!", // cass_bigsmile
            "Pardon my discourtesy.", // cass_eyesclosed
            "I'm Cass, and you are..?", // cass_neutral
            "Ah! You've fallen haven't you?", // cass_affectionate
            "Oh dear.",
            "You must have hit your head pretty hard... Can you remember your name?", //13

            //entering name!!

            "[i]*That was unusually hard to remember. Maybe I really did hit my head...*[/i]", //14
            "Cute little human... Can you stand?",
            "Let me take you to my humble abode. I will treat you with great care.", // cass_scarysmile   //16

            //first choice, option 1 : "Go with her" storyline

            "[i]What a nice lady... taking care of those in need.[/i]", //17
            "Hold it right there.", //ahri_wolf appears!
            "!?", //cass_shocked
            "That \"cute little\" human isn't going anywhere with you, Cass.",
            "And why might that be, Ahri? They need to be taken care of, and i'm perfectly capable of doing so!",
            "I'm too sleepy for this... hand them over. Now.",
            "Moreover, they want to follow me. Just let me have this one will ya?",
            "They're dumb anyways, nobody will notice their absence.", //cass_scarysmile
            "Very well...",
            "You asked for this.", //ahri_annoyed
            "Ugh...", //cass_defeated
            "Ughhh... Why did you have to be decieved so easily? I had to transform too...", //cass leaves, ahri is annoyed still
            "Anyways, im Ahri, the guide. Unlike some others here, i'll follow you whether you accept following me, or choose forging your own path.", //ahri_neutral
            "I will watch over you, whether you're enjoying or suffering. My job is to keep you from losing your path, that's-", //ahri_smile
            "Travel where?! What is this place? Guide for what? To fix the loose wiring in my head?!",//ahri_neutral
            "...calm down, human. I understand you've had your fair share of encounters with some too friendly entities, but... ",
            "I am not your enemy.", //ahri_affectionate
            "of course, the choice is yours. It has always been.",  //34

            //start your journey option only, ending 1 of scene1


            //second choice, option 2: "Refuse" storyline

            "Maybe you would like to rest for a bit..?", //cass_affectionate   //35
            "...",
            "Ugh, forget it. We don't have much time before that stupid wolf appears-",
            "...", //ahri_wolf appears
            "You choose to leave me this time, human. But we'll meet again.", //cass_scarysmile
            "Welcome, human. I'm Ahri, the guide. Unlike some others here, i'll follow you whether you accept following me or choose forging your own path.",
            "You need not worry, for i am not your enemy. But there will be bad omens on your journey.",
            "I will always watch over you, whether you're enjoying or suffering. My job is to keep you from losing your path, that's all.",
            "The choice of following me or forging your own path is yours, human. It has always been." //43

            //start your journey option only, ending 2 of scene1

    };
    private int dialogueIndex = 0;
    private int charIndex = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable textAnimator;
    private ImageView characterImageView, characterCutout, ahriImageView, ahriCutout;

    private void startTextAnimation() {
        textAnimator = new Runnable() {
            @Override
            public void run() {
                String currentDialogue = dialogues[dialogueIndex];
                if (charIndex < currentDialogue.length()) {
                    // Check for italics using [i] and [/i] custom tags
                    if (currentDialogue.startsWith("[i]", charIndex)) {
                        dialogueText.append(Html.fromHtml("<i>"));
                        charIndex += 3; // Skip "[i]"
                    } else if (currentDialogue.startsWith("[/i]", charIndex)) {
                        dialogueText.append(Html.fromHtml("</i>"));
                        charIndex += 4; // Skip "[/i]"
                    } else {
                        // Add next character
                        dialogueText.append(String.valueOf(currentDialogue.charAt(charIndex)));
                        charIndex++;
                    }
                    handler.postDelayed(this, 50); // Delay for the next character
                } else {
                    handler.removeCallbacks(this);
                }
            }
        };
        handler.post(textAnimator);
    }

    private void showNextDialogue() {
        if (dialogueIndex < dialogues.length) {
            charIndex = 0;
            dialogueText.setText("");

            // Handle character appearance and expressions
            if (dialogueIndex == 5) {
                characterImageView.setVisibility(View.VISIBLE);
                characterCutout.setVisibility(View.VISIBLE);

            } else if (dialogueIndex == 8) {
                updateCharacterExpression(characterImageView, R.drawable.cass_big_smile);
                updateCharacterExpression(characterCutout, R.drawable.cass_bigsmile_cutout);

            } else if (dialogueIndex == 9) {
                updateCharacterExpression(characterImageView, R.drawable.cass_eyes_closed);
                updateCharacterExpression(characterCutout, R.drawable.cass_eyesclosed_cutout);

            } else if (dialogueIndex == 10) {
                updateCharacterExpression(characterImageView, R.drawable.cass_neutral);
                updateCharacterExpression(characterCutout, R.drawable.cass_neutral_cutout);

            } else if (dialogueIndex == 11) {
                updateCharacterExpression(characterImageView, R.drawable.cass_affectionate);
                updateCharacterExpression(characterCutout, R.drawable.cass_affectionate_cutout);

            } else if (dialogueIndex == 13) {
                if (nameInput.getVisibility() != View.VISIBLE) {
                    showNameInput();
                    return; // Wait for name input before proceeding
                }

            } else if (dialogueIndex == 16) {
                updateCharacterExpression(characterImageView, R.drawable.cass_scary_smile);
                updateCharacterExpression(characterCutout, R.drawable.cass_scarysmile_cutout);
                showDialogueOptions(
                        "Go with her",
                        "Refuse",
                        () -> {
                            // Option 1 action
                            dialogueIndex = 17; // Skip to option 1 storyline
                            showNextDialogue();
                            if (dialogueIndex == 18) {
                                ahriImageView.setVisibility(View.VISIBLE); //make ahri_wolf visible !!!
                                ahriCutout.setVisibility(View.VISIBLE);
                                characterCutout.setVisibility(View.GONE);
                                updateCharacterExpression(ahriImageView, R.drawable.ahri_wolf);
                                updateCharacterExpression(ahriCutout, R.drawable.ahri_wolf);
                            } else if (dialogueIndex == 19) {
                                updateCharacterExpression(characterImageView, R.drawable.cass_angry);
                                updateCharacterExpression(characterCutout, R.drawable.cass_angry_cutout);
                                characterCutout.setVisibility(View.VISIBLE);
                                ahriCutout.setVisibility(View.GONE);
                            }  else if (dialogueIndex == 20) {
                                ahriCutout.setVisibility(View.VISIBLE);
                                characterCutout.setVisibility(View.GONE);
                            }  else if (dialogueIndex == 21) {
                                characterCutout.setVisibility(View.VISIBLE);
                                ahriCutout.setVisibility(View.GONE);
                            }  else if (dialogueIndex == 22) {
                                ahriCutout.setVisibility(View.VISIBLE);
                                characterCutout.setVisibility(View.GONE);
                            }  else if (dialogueIndex == 23) {
                                characterCutout.setVisibility(View.VISIBLE);
                                ahriCutout.setVisibility(View.GONE);
                            }  else if (dialogueIndex == 24) {
                                updateCharacterExpression(characterImageView, R.drawable.cass_scary_smile);
                                updateCharacterExpression(characterCutout, R.drawable.cass_scarysmile_cutout);
                            } else if (dialogueIndex == 26) {
                                updateCharacterExpression(characterImageView, R.drawable.cass_angry);
                                //updateCharacterExpression(characterCutout, R.drawable.cass_angry_cutout);
                                updateCharacterExpression(ahriImageView, R.drawable.ahri_annoyed);
                                //updateCharacterExpression(ahriCutout, R.drawable.ahri_annoyed_cutout);
                                updateCharacterExpression(characterCutout, R.drawable.ahri_annoyed_cutout);
                            } else if (dialogueIndex == 27) {
                                updateCharacterExpression(characterImageView, R.drawable.cass_eyes_closed);
                                updateCharacterExpression(characterCutout, R.drawable.cass_eyesclosed_cutout);
                            } else if (dialogueIndex == 28) {
                                characterImageView.setVisibility(View.GONE); //cass leaves ??!?!?!
                                characterCutout.setVisibility(View.GONE);
                            } else if (dialogueIndex == 29) {
                                updateCharacterExpression(ahriImageView, R.drawable.ahri_neutral);
                                updateCharacterExpression(ahriCutout, R.drawable.ahri_neutral_cutout);
                            } else if (dialogueIndex == 30) {
                                updateCharacterExpression(ahriImageView, R.drawable.ahri_smile);
                                updateCharacterExpression(ahriCutout, R.drawable.ahri_smile_cutout);
                            } else if (dialogueIndex == 31) {
                                updateCharacterExpression(ahriImageView, R.drawable.ahri_neutral);
                                updateCharacterExpression(ahriCutout, R.drawable.ahri_neutral_cutout);
                            } else if (dialogueIndex == 33) {
                                updateCharacterExpression(ahriImageView, R.drawable.ahri_heartbroken);
                                updateCharacterExpression(ahriCutout, R.drawable.ahri_heartbroken_cutout);
                            }
                            else if (dialogueIndex == 34){
                                Toast.makeText(this, "End of story for now!", Toast.LENGTH_SHORT).show();
                            }
                        },
                        () -> {
                            // Option 2 action
                            dialogueIndex = 35; // Skip to option 2 storyline
                            showNextDialogue();
                            if (dialogueIndex == 35){
                                updateCharacterExpression(characterImageView, R.drawable.cass_affectionate);
                                updateCharacterExpression(characterCutout, R.drawable.cass_affectionate_cutout);
                            } else if (dialogueIndex == 38){
                                ahriImageView.setVisibility(View.VISIBLE);  //ahri appears!!
                                ahriCutout.setVisibility(View.VISIBLE);
                                updateCharacterExpression(ahriImageView, R.drawable.ahri_wolf);
                                updateCharacterExpression(ahriCutout, R.drawable.ahri_wolf);
                            } else if (dialogueIndex == 39){
                                updateCharacterExpression(characterImageView, R.drawable.cass_scary_smile);
                                updateCharacterExpression(characterCutout, R.drawable.cass_scarysmile_cutout);
                            } else if (dialogueIndex == 40){
                                characterImageView.setVisibility(View.GONE); //cass left??!!?!?!
                                characterCutout.setVisibility(View.GONE);
                            } else if (dialogueIndex == 43) {
                                Toast.makeText(this, "End of story for now!", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                return; // Prevent continuing the normal dialogue flow
            }

            // Animate the current dialogue line
            startTextAnimation();
            dialogueIndex++; // Move to the next dialogue for the next trigger
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Find dialogue options
        dialogueOptionsLayout = findViewById(R.id.dialogueOptionsLayout);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);

        // Hide by default
        dialogueOptionsLayout.setVisibility(View.GONE);

        // Initialize dialogue system as usual
        dialogueText = findViewById(R.id.dialogueText);

        // Initialize views
        dialogueText = findViewById(R.id.dialogueText);
        nameInput = findViewById(R.id.nameInput);

        //cass and ahri sprites
        characterImageView = findViewById(R.id.cass_sprite);
        characterCutout = findViewById(R.id.characterCutout);
        ahriImageView = findViewById(R.id.ahri_sprite);
        ahriCutout = findViewById(R.id.ahri_cutout);

        // Set default expressions

        updateCharacterExpression(characterImageView, R.drawable.cass_neutral);
        characterImageView.setVisibility(View.GONE);
        characterCutout.setVisibility(View.GONE);
        ahriImageView.setVisibility(View.GONE);
        ahriCutout.setVisibility(View.GONE);

        // Load saved player name
        SharedPreferences preferences = getSharedPreferences("GameData", MODE_PRIVATE);
        playerName = preferences.getString("playerName", "Player");

        // Start text animation
        startTextAnimation();
    }

    private void showDialogueOptions(String option1Text, String option2Text, Runnable option1Action, Runnable option2Action) {
        // Set button texts
        option1.setText(option1Text);
        option2.setText(option2Text);

        // Show the layout
        dialogueOptionsLayout.setVisibility(View.VISIBLE);

        // Handle option clicks
        option1.setOnClickListener(v -> {
            dialogueOptionsLayout.setVisibility(View.GONE); // Hide options
            option1Action.run(); // Perform the action for option 1
            Log.d("DialogueOption","Option 1 is selected");
        });

        option2.setOnClickListener(v -> {
            dialogueOptionsLayout.setVisibility(View.GONE); // Hide options
            option2Action.run(); // Perform the action for option 2
            Log.d("DialogueOption","Option 2 is selected");
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SPACE || keyCode == KeyEvent.KEYCODE_ENTER) {
            if (nameInput.getVisibility() == View.VISIBLE) return true; // Ignore input during name entry
            showNextDialogue();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void updateCharacterExpression(ImageView characterSprite, int drawableResId) {
        characterSprite.setImageResource(drawableResId);
    }

    @SuppressLint("SetTextI18n")
    private void showNameInput() {
        nameInput.setVisibility(View.VISIBLE);
        nameInput.requestFocus();

        // Open the keyboard
        getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        nameInput.setOnEditorActionListener((v, actionId, event) -> {
            String input = nameInput.getText().toString().trim();
            if (!input.isEmpty()) {
                playerName = input;
                nameInput.setVisibility(View.GONE); // Hide input
                android.view.inputmethod.InputMethodManager imm =
                        (android.view.inputmethod.InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(nameInput.getWindowToken(), 0);
                }

                // Insert "Nice to meet you" and continue
                dialogueText.setText("Nice to meet you, " + playerName + "!");
                dialogueIndex++; // Advance to the next dialogue
                showNextDialogue(); // Resume normal dialogue flow
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(textAnimator);
    }
}