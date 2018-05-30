<template>
    <b-container class="Navigation" fluid>
        <navbar></navbar>
        <br>
        <b-row>
          <b-breadcrumb :items="items" style="position: relative;left: 41px;"/>
        </b-row>
        <b-row>
          <b-col>
            <h2> Create New Sprint</h2>
          </b-col>
          <b-col></b-col>
          <b-col></b-col>
          <b-col></b-col>
        </b-row>
            <b-row>
                <p></p>
            </b-row>
        <b-card style="position: relative;left: 5px;" border-variant="light">
            <b-row>
                <label style="position: relative;left:25px">Title:</label>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-input v-model="getTitle" type="text" placeholder="Type Project's title"></b-form-input>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row>
                <label style="position: relative;left:25px">Plan:</label>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-input v-model="getPlan" type="text" placeholder="Type the project's plan"></b-form-input>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row>
                <label style="position: relative;left:25px">Ends:</label>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-input  v-model="getDeadline" type="date"></b-form-input>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row>
                <label style="position: relative;left:25px">Goal:</label>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-input v-model="getGoal" type="text" placeholder="Type project's goal"></b-form-input>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row>
                <p></p>
            </b-row>
            <b-row>
                <b-col align-self="center">
                    <h3> Add user stories </h3>
                </b-col>
            </b-row>
            <b-row>
                <p></p>
            </b-row>
            <b-row>
                <div class="line">.</div>
            </b-row>
            <b-row>
                <b-col>
                    <b-dropdown id="ddown1" text="Select Epic" variant="default" class="m-md-2">
                        <b-dropdown-item> Epic #1 </b-dropdown-item>
                        <b-dropdown-item> Epic #2 </b-dropdown-item>
                        <b-dropdown-item> List Of Indepented User Stories </b-dropdown-item>
                    </b-dropdown>
                </b-col>
                <b-col>
                    <b-btn v-b-toggle.collapse1 variant="default"> Select User Stories</b-btn>
                    <b-collapse id="collapse1" class="mt-2">
                        <b-form-group>
                            <b-form-checkbox-group buttons v-model="selected" stacked :options="options">
                            </b-form-checkbox-group>
                        </b-form-group>
                    </b-collapse>
                    <b-button size="sm" variant="primary" style="font-weight: bolder"> + </b-button>
                </b-col>
                <b-col>
                    <b-btn v-b-toggle.collapse3 class="m-1" variant="default">Toggle Collapse</b-btn>
                    <b-collapse visible id="collapse3">
                        <b-jumbotron class="text-center" >
                            <div>
                             User Story 1
                            <b-button size="sm"> Delete </b-button>
                            </div>
                        </b-jumbotron>
                    </b-collapse>
                </b-col>
            </b-row>
            <b-row>
                <p></p>
            </b-row>
            <b-row>
                <div class="line">.</div>
            </b-row>
            <b-row>
                <b-col></b-col>
                <b-col></b-col>
                <b-col></b-col>
                <b-col align-self="end">
                    <b-button size="lg" variant="success">
                        Create new sprint
                    </b-button>
                </b-col>
            </b-row>
        </b-card>
        <div>
            <h4> Will sumbit: </h4>
            <p>Title: {{ getTitle }}</p>
            <p>Plan: {{ getPlan }}</p>        
            <p>Goal: {{ getGoal }}</p>
            <p>Deadline: {{ getDeadline }}</p>
        </div>
    </b-container>
</template>

<script>
    import Navbar from "./Navbar.vue"
    export default {
        components: {
            navbar: Navbar,
        }
        ,data() {
            return {
                getTitle: '',
                getPlan:'',
                getDeadline:'',
                getGoal:'',
                selected: [], // Must be an array reference!
                options: [{
                    text: 'User Story 1',
                    value: 'orange'
                }, {
                    text: 'User Story 2',
                    value: 'apple'
                }, {
                    text: 'User Story 3',
                    value: 'pineapple'
                }],
                items: [{
                text: 'Home',
                href: '/#/'
              }, {
                text: 'Incurance App',
                href: '/#/projectpageoverview'
              }, {
                text: 'Create New Sprint',
                active: true
              }]
            }
        }
    }
</script>

<style scoped>
    .line {
        width: 99%;
        font-size: 1px;
        line-height: 2px;
        background-color: lavender;
        margin-top: -6px;
        margin-bottom: 10px;
    }
    
    .Navigation {
        position: absolute;
        top: 0;
        left: 0;
    }
    
    .container-fluid {
        padding-right: 0;
        padding-left: 0;
    }
    
</style>