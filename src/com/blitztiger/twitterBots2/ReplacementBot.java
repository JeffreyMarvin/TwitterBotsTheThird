package com.blitztiger.twitterBots2;

import java.util.*;

import winterwell.jtwitter.Twitter.Status;

public class ReplacementBot extends TwitterBot {
	
	private Map<String, String> replacements;

	public ReplacementBot(String username, Map<String, String> replacements) {
		super(username);
		this.replacements = replacements;
	}

	public ReplacementBot(String username, String filePath, Map<String, String> replacements) {
		super(username, filePath);
		this.replacements = replacements;
	}

	@Override
	public void runBot(boolean publicTimeline, String userToGetTimelineOf) throws Exception {
		List<Status> timeline;
		List<String> alreadyTweeted = new ArrayList<String>();
		while(true){
			if(publicTimeline){
				timeline = twitter.getPublicTimeline();
			} else if (userToGetTimelineOf != null){
				timeline = twitter.getUserTimeline(userToGetTimelineOf);
			} else {
				timeline = twitter.getHomeTimeline();
			}
			for(Status s : timeline){
				String tweet = s.getText();
				if(!alreadyTweeted.contains(tweet)){
					alreadyTweeted.add(tweet);
					//System.out.println(tweet);
					boolean keepGoing = true;
					while(keepGoing){
						keepGoing = false;
						for(String r : replacements.keySet()){
							while(tweet.contains(r)){
								keepGoing = true;
								tweet = tweet.replace(r, replacements.get(r));
							}
						}
					}
					if(!tweet.equals(s.getText())){
						tweet = "#sexd @" + s.getUser().getScreenName() + ": " + tweet;
						if(tweet.length() > 140){
							tweet = tweet.substring(0, 137) + "...";
						}
						boolean shouldITweet = tweet.toLowerCase().contains("sex");
						if(shouldITweet){
							for(Status t : twitter.getUserTimeline()){
								if(t.getText().equals(tweet)){
									shouldITweet = false;
									break;
								}
							}
						}
						if(shouldITweet){
							System.out.println("Tweeting:\n\t" + tweet);
							twitter.setStatus(tweet);
						}
					}
				}
			}
			System.out.println("Waiting to avoid going over the rate limit");
			Thread.sleep(waitBetweenRequestTime);
		}
	}
	
	public static void main(String[] args){
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put(" ex", " sex");
		replacements.put(" eX", " seX");
		replacements.put(" Ex", " Sex");
		replacements.put(" EX", " SEX");
		replacements.put(" an sex", " a sex");
		replacements.put(" an seX", " a seX");
		replacements.put(" an Sex", " a Sex");
		replacements.put(" an SEX", " a SEX");
		replacements.put(" An sex", " A sex");
		replacements.put(" An seX", " A seX");
		replacements.put(" An Sex", " A Sex");
		replacements.put(" An SEX", " A SEX");
		replacements.put(" aN sex", " a sex");
		replacements.put(" aN seX", " a seX");
		replacements.put(" aN Sex", " a Sex");
		replacements.put(" aN SEX", " a SEX");
		replacements.put(" AN sex", " A sex");
		replacements.put(" AN seX", " A seX");
		replacements.put(" AN Sex", " A Sex");
		replacements.put(" AN #SEX", " A #SEX");
		replacements.put(" #ex", " #sex");
		replacements.put(" #eX", " #seX");
		replacements.put(" #Ex", " #Sex");
		replacements.put(" #EX", " #SEX");
		replacements.put(" an #sex", " a #sex");
		replacements.put(" an #seX", " a #seX");
		replacements.put(" an #Sex", " a #Sex");
		replacements.put(" an #SEX", " a #SEX");
		replacements.put(" An #sex", " A #sex");
		replacements.put(" An #seX", " A #seX");
		replacements.put(" An #Sex", " A #Sex");
		replacements.put(" An #SEX", " A #SEX");
		replacements.put(" aN #sex", " a #sex");
		replacements.put(" aN #seX", " a #seX");
		replacements.put(" aN #Sex", " a #Sex");
		replacements.put(" aN #SEX", " a #SEX");
		replacements.put(" AN #sex", " A #sex");
		replacements.put(" AN #seX", " A #seX");
		replacements.put(" AN #Sex", " A #Sex");
		replacements.put(" AN #SEX", " A #SEX");
		
		ReplacementBot bot = new ReplacementBot("ProfessorSex", "/home/jeff/workspace/TwitterBots/sexFile.txt", replacements);
		while(true){
			try{
				bot.runBot(false, null);
			} catch (Exception e){
				e.printStackTrace();
				System.out.println("Whoops, there was a fail... let's try that again in a minute...");
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

}
