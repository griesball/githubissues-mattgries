package com.alienvault.service;

import com.alienvault.model.Issue;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.kohsuke.github.*;
import com.alienvault.model.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class IssueService {

    //Key inputs
    private String[] inputs;
    private GitHub gitHub;

    //Variables for storing output
    private List<InvalidInput> invalidInputTracker = new ArrayList<InvalidInput>();
    private HashMap<String,List<Issue>> issues = new HashMap<String, List<Issue>>();

    //Variables used to track which
    private String dateWithMostIssues = "";
    private Integer mostIssueCount = 0;

    //Service for converting java objects to json
    private Gson json = new Gson();

    public IssueService(String[] inputRepos) throws IOException {
        this.inputs = inputRepos;
        this.gitHub = GitHub.connectAnonymously();
    }

    //Main call into issue service
    //Works through input list to retrieve information
    public String generateIssueReport(){
        for (Integer i = 0; i < inputs.length; i++){
            processSingleInput(i);
        }
        return formatReturnData();
    }

    //Retrieves issues associated with single repo
    //type:issue
    //repo:{arg}
    //I was worried about data being paged but the repo.listIssues method already steps through the pages to build a single list.
    //And the getIssues method uses the listIssues method and converts it to a list
    private void processSingleInput(Integer index){
        if(inputs[index].trim().matches(".*\\/.*")){
            GHRepository repo;
            List<GHIssue> issuesForRepo;
            try {
                repo = gitHub.getRepository(inputs[index]);
            }
            catch (IOException e){
                System.out.println(inputs[index] + " : Cannot access repo.");
                invalidInputTracker.add(new InvalidInput(inputs[index],"Cannot access repo."));
                return;
            }
            try {
                issuesForRepo = repo.getIssues(GHIssueState.ALL);
            }
            catch (IOException e){
                System.out.println(inputs[index] + " : Cannot access repo.");
                invalidInputTracker.add(new InvalidInput(inputs[index],"Cannot access repo."));
                return;
            }
            try {
                issuesForRepo.stream().forEach(t -> {
                    String tempDate = "", tempDateFormatted = "";
                    DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    try{
                        tempDate = t.getCreatedAt().toString();
                        tempDateFormatted = sdf.format(t.getCreatedAt()).toString();
                    }
                    catch (IOException e){
                        System.out.println("Created Date throws an IOException that must be caught.");
                    }
                    Issue tempIssue = new Issue(t.getId(),t.getState().toString(),t.getTitle(),"" + t.getRepository().getFullName() + "",tempDate);
                    List<Issue> tempList = new ArrayList<Issue>();
                    if(issues.get(tempDateFormatted) != null){
                        tempList = issues.get(tempDateFormatted);
                        tempList.add(tempIssue);
                        issues.replace(tempDateFormatted,tempList);
                    }else{

                        tempList.add(tempIssue);
                        issues.put(tempDateFormatted, tempList);
                    }
                    if(issues.get(tempDateFormatted).size() > mostIssueCount){
                        mostIssueCount = issues.get(tempDateFormatted).size();
                        dateWithMostIssues = tempDateFormatted;
                    }
                });
            }
            catch (Exception e){
                System.out.println(e.toString());
            }

        }else{
            System.out.println(inputs[index] + " : Input doesn't meet owner/repo format.");
            invalidInputTracker.add(new InvalidInput(inputs[index],"Input doesn't meet owner/repo format."));
        }
    }

    //Build Json return object using GSON library
    //Assemble hashmap to match desired results
    private String formatReturnData(){
        Map<String,Object> reportFormat = new HashMap<String,Object>();
        if(issues.size() > 0) {
            reportFormat.put("issues", issues);
            reportFormat.put("top_day", buildTopDayReport());
        }else{
            reportFormat.put("Report", "No results found");
            reportFormat.put("Invalid Inputs", invalidInputTracker.toString());
        }
        return json.toJson(reportFormat);
    }

    //Build top day report using gathered information of dateWithMostIssues
    private HashMap<String,Object> buildTopDayReport(){
        HashMap<String,Object> topDayReport = new HashMap<String,Object>();
        HashMap<String,Integer> occurrencesReport = new HashMap<String,Integer>();
        topDayReport.put("day",dateWithMostIssues);
        List<Issue> tmpList = issues.get(dateWithMostIssues);
        tmpList.stream().forEach(t -> {
            if(occurrencesReport.get(t.getRepository()) == null){
                occurrencesReport.put(t.getRepository(),0);
            }
            Integer count = occurrencesReport.get(t.getRepository()) + 1;
            occurrencesReport.put(t.getRepository(),count);
        });
        topDayReport.put("occurrences", occurrencesReport);
        return topDayReport;
    }

}
