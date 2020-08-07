package com.valorin.commands;


public class SimilarityComparer {
	public static float getSubCommandSimilarity(String enter, String subcommand) {
	    int d[][];
	    int n = enter.length();
	    int m = subcommand.length();
	    int i;
	    int j;
	    char ch1;
	    char ch2;
	    int temp;
	    if (n == 0 || m == 0) {
	        return 0;
	    }
	    d = new int[n + 1][m + 1];
	    for (i = 0; i <= n; i++) {
	        d[i][0] = i;
	    }

	    for (j = 0; j <= m; j++) {
	        d[0][j] = j;
	    }

	    for (i = 1; i <= n; i++) {
	        ch1 = enter.charAt(i - 1);
	        for (j = 1; j <= m; j++) {
	            ch2 = subcommand.charAt(j - 1);
	            if (ch1 == ch2 || ch1 + 32 == ch2 || ch2 + 32 == ch1) {
	                temp = 0;
	            } else {
	                temp = 1;
	            }
	            d[i][j] = Math.min(
	            		Math.min(d[i - 1][j] + 1, 
	            		d[i][j - 1] + 1),
	            		d[i - 1][j - 1] + temp);
	        }
	    }
	    return (1 - (float) d[n][m] / Math.max(enter.length(), subcommand.length())) * 100F;
	}
	
	public static final String[] SUBCOMMANDS = 
		{"help",
		 "start",
		 "timetable",
		 "ainfo",
		 "send",
		 "accept",
		 "deny",
		 "quit",
		 "shop",
		 "records",
		 "points",
		 "rank",
		 "lobby",
		 "dan",
		 "energy",
		 "watch",
		 "changelang",
		 "arena",
		 "hd",
		 "stop",
		 "game",
		 "reload",
		 "checkv",
		 "sendall"};
	
	public static String getMostSimilarSubCommand(String enter) {
	  String mostSimilarSubCommand = null;
	  float similarity = 0;
	  for (String subCommand : SUBCOMMANDS) {
		float t = getSubCommandSimilarity(enter, subCommand);
		if (t > similarity) {
		  similarity = t;
		  mostSimilarSubCommand = subCommand;
		}
	  }
	  return mostSimilarSubCommand;
	}
}
