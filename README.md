## STC-Alexa
This is the STC-Alexa skill created for the Student Technology Center at Western Washington University. 


## Getting Started
To add on to this code, you'll first need to create a class for your Intent Handling in the package:
```
edu.wwu.center.studenttechnology.intentHandlers
```

The class can be named anything you'll like. Afterwards, in the main declaration of the class, extend *IntentHandlerBase*.

```
public class ExampleIntent extends IntentHandlerBase
```

This will cause your IDE to want to implement the following methods:
```
    public ExampleIntent() {
        super("ExampleIntent");
    }

    @Override
    public SpeechletResponse execute(Intent intent, Session session) {}

    @Override
    public SpeechletResponse handleYesResponse(Intent intent, Session session) {}

    @Override
    public SpeechletResponse handleNoResponse(Intent intent, Session session) {}

    @Override
    public SpeechletResponse handleNextIntent(Intent intent, Session session) {}
```
The constructor of your method MUST contain the super constructor, given the name of your Intent. 

#### Execute
The execute method is the first code that is ran for your Intent. You can add simple responses for when users invoke your Intent. You can also pose a question and handle that logic in the other methods.

#### HandleYes/NoResponse
If you choose to ask a question to the user, you can use *handleYesResponse*, and *handleNoResponse* (respectively) to handle yes and no responses. Here you can continue to pose questions or simply give an ending response. If you do not plan to ask the user a yes or no question, both of these can simply be set to return null.

#### HandleNextIntent
If your code requires a bit more delicate handling of the response, you can handle that logic in the *handleNextIntent* method. If you set your session (more on this below) for your handler to handle the next intent, anything the user says will be given to your handler. Example, if you were to set the session to pass the next intent to you, they may something that typically invokes a different response, but will not instead be passed to your code to handle. If you do not plan to utilize this, simply set this method to return null.

### SpeechletResponse

A SpeechletResponse is what you'll use to respond to users. Its constructor takes no arguments. In your IntentHandler, you can speak to the user using the *newTellResponse* method:
```
return SpeechletResponse.newTellResponse("Hello, it's a nice day out!");
```

You can also pose a question using newAskResponse method, it takes in 2 arguments: *(String response, String reprompt)*, the main output text, and a *reprompt* text in case the user didn't hear clearly enough. 

```
return SpeechletResponse.newAskResponse("Hello! How are you?", "I asked, how are you?");
```

### Session
If you plan to ask the user a question, you MUST register your IntentHandler to handle the incoming response. To handle the next yes/no intent, use:
```
SessionUtil.setIntentToHandleNextYesNo(session, getName());
```

*GetName()* will return your intent name for you! To handle whatever the next response is, use:
```
SessionUtil.setIntentToHandleNextEvent(session, getName());
```

This can be placed anywhere before your code returns a SpeechletResponse.

### Wrapping Up

Your code may look like the following:
```
public class ExampleIntent extends IntentHandlerBase {

    public ExampleIntent(String name) {
        super(name);
    }

    @Override
    public SpeechletResponse execute(Intent intent, Session session) {
        SessionUtil.setIntentToHandleNextYesNo(session, getName());
		SessionUtil.setIntentToHandleNextEvent(session, getName());

		return SpeechletResponse.newAskResponse("Hello! Are you having a good day?", "I asked, are you having a good day?");
    }

    @Override
    public SpeechletResponse handleYesResponse(Intent intent, Session session) {
        return SpeechletResponse.newTellResponse("Great to hear!");
    }

    @Override
    public SpeechletResponse handleNoResponse(Intent intent, Session session) {
        return SpeechletResponse.newTellResponse("Oh no!");
    }

    @Override
    public SpeechletResponse handleNextIntent(Intent intent, Session session) {
        return SpeechletResponse.newTellResponse("I'm sorry, I didn't understand your response!");
    }
}

```

Once you're here, you're almost done! Open the *STCAlexaSpeechlet* class and register your IntentHandlerBase in the main IntentHandler class. Declare the following AFTER the creation of *IntentHandler()*...

```
ExampleIntent exampleIntent = new ExampleIntent("ExampleIntent");
intentHandler.addIntentHandler(exampleIntent.getName(), exampleIntent);
```

addIntentHandler takes in a String and a your IntentHandlerBase object. Go ahead and save this file...

Next, follow the example in the *SampleUtterances.txt* file in resources. Your edits may look like this:
```
ExampleIntent Fire Up Example Intent
ExampleIntent Open Example Intent
```

Here you'll add sample ways to trigger your Intent. You will also need to add your Intent in the *IntentSchema.json* which will look like this:
```
        {
            "intent": "ExampleIntent"
        }
```

Be sure to have your JSON entry in correctly, look at the other intents if you're not sure!

Finally, compile the jar using 'mvn assembly:assembly -DdescriptorId=jar-with-dependencies package' and upload to AWS Amazon and update Dev Amazon if needed.
