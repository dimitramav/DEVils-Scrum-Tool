<template>
    <b-container>
        <div class="sidenav">
            <a href="#Overview">Overview</a>
            <a id="pressed" href="#Backlog">Backlog</a>
            <a href="#Sprints">Sprints</a>
        </div>
        <b-row>
          <b-breadcrumb :items="items" style="position: relative;left: 41px;"/>
        </b-row>
        <b-row>
          <b-col align-self="center">
            <h2> Create New Sprint</h2>
          </b-col>
        </b-row>
        <b-card style="position: relative;left: 30px;">
            <b-row>
                <label>Title:</label>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-input></b-form-input>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row>
                <label>Plan:</label>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-input></b-form-input>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row>
                <label>Ends:</label>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-input type="date"></b-form-input>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row>
                <label>Goal:</label>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-input></b-form-input>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row>
                <b-col align-self="center">
                    <h3> Add user stories </h3>
                </b-col>
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
                    <b-button size="sm" variant="warning"> Add Selected User Stories </b-button>
                </b-col>
                <b-col>
                    <b-btn v-b-toggle.collapse3 class="m-1">Toggle Collapse</b-btn>
                    <b-collapse visible id="collapse3">
                        <b-card> User Story 1
                            <b-button size="sm"> Delete </b-button>
                        </b-card>
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
                <b-col align-self="end">
                    <b-button size="lg" variant="danger">
                        Create new sprint
                    </b-button>
                </b-col>
            </b-row>
        </b-card>
    </b-container>
</template>

<script>
    export default {
        data() {
            return {
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
                href: '#'
              }, {
                text: 'AppName',
                href: '#'
              }, {
                text: 'Overview',
                href: '#'
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
        position: fixed;
        top: 0;
        left: 0;
    }
    
    .container-fluid {
        padding-right: 0;
        padding-left: 0;
    }
    
    .sidenav {
        height: 100%;
        width: 140px;
        position: fixed;
        z-index: -1;
        top: 0;
        left: 0;
        background-color: #495457;
        padding-top: 100px;
    }
    
    .sidenav a {
        padding: 20px 8px 20px 16px;
        text-decoration: none;
        font-family: Courier New;
        font-size: 20px;
        color: white;
        display: block;
    }
    
    .sidenav a:hover {
        color: orange;
    }
    
    #pressed {
        background-color: #3B4446;
        font-weight: normal;
    }
</style>