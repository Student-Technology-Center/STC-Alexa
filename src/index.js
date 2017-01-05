exports.handler = function (event, context)
{
	try
	{
		console.log("event.session.application.applicationID=" + event.session.application.applicationId);
		
		// If the session is new, we call the onSessionStart function with the
		// request ID on our session
		if (event.session.new) {
			onSessionStart({requestId: event.request.requestID}, event.session);
		}
		
		// A callback method for launchRequest and IntentRequest
		callback = function callback(sessionAttributes, speechletResponse) {
			context.succeed(buildResponse(sessionAttributes, speechletResponse));
		}
		
		// We run different methods depending on our event request type
		switch(event.request.type)
		{
		case "LaunchRequest":
			onLaunch(event.request, event.session, callback);
		case "IntentRequest":
			handleIntent(event.request, event.session, callback);
		case "SessionEndedRequest":
			onSessionEnd(event.request, event.session);
			context.succeed();
		}
	}
	catch (e)
	{
		context.fail("Exception: " + e);
	}
};

function onSessionStart(sessionStartRequest, session)
{
	console.log("onSessionStart requestID=" + sessionStartRequest.requestID + ", sessionID=" + session.sessionID);
	
	
	// Session Start Logic Here
}

function onSessionEnd()
{
	// Session End Logic
}

function onLaunch(launchRequest, session, callback)
{
	console.log("onLaunch requestID=" + launchRequest.requestId + ", sessionID=" + session.sessionId);
	
	// Launch Logic
	// This is called when a user invokes this specific 'skill' but without giving an intent
}

function handleIntent(intentRequest, session, callback)
{
	console.log("handleIntent requestID=" + intentRequest.requestId + ", sessionID=" + session.sessionId);
	
	// Handle Intents Here
}
